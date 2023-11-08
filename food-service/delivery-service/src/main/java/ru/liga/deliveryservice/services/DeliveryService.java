package ru.liga.deliveryservice.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.dto.RabbitMessage;
import ru.liga.common.models.Courier;
import ru.liga.common.models.Order;
import ru.liga.common.models.enums.CourierStatus;
import ru.liga.common.models.enums.OrderStatus;
import ru.liga.common.repositories.CourierRepository;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.common.util.DistanceCalculator;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.deliveryservice.mappers.DeliveryMapper;
import ru.liga.deliveryservice.dto.DeliveriesResponse;
import ru.liga.deliveryservice.dto.DeliveryDTO;
import ru.liga.deliveryservice.dto.DeliveryDistances;
import ru.liga.deliveryservice.services.rabbitProducerService.RabbitProducerServiceImpl;

import java.util.Comparator;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final DeliveryMapper deliveryMapper;
    private final RabbitProducerServiceImpl rabbitProducerService;
    private final ObjectMapper objectMapper;


    public DeliveriesResponse getDeliveriesResponse(int page, int size) {

        Courier courier = courierRepository.findAll().get(0);  /*Заглушка*/

        Page<DeliveryDTO> deliveries = orderRepository
                .findAllByStatus(PageRequest.of(page, size), OrderStatus.KITCHEN_READIED)
                .map(order -> deliveryMapper.orderToDeliveryDTO(order, "some payment",
                        getDistances(order.getId(), courier.getId())));

        return new DeliveriesResponse(deliveries.getContent(), deliveries.getNumber(), deliveries.getSize());
    }

    @Transactional
    @SneakyThrows
    public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException("Order with orderId = " + orderId + " is not found"));

        Courier courier = courierRepository.findAll().get(0);  /*Заглушка*/

        if (orderStatus.equals(OrderStatus.DELIVERY_ACCEPTED)) {
            courier.setStatus(CourierStatus.COURIER_NOT_AVAILABLE);
            courierRepository.save(courier);
            order.setCourier(courier);
            orderRepository.save(order);

            RabbitMessage messageForOrderService = new RabbitMessage
                    (orderId, "order", "Курьер назначен.");
            RabbitMessage messageForKitchenService = new RabbitMessage
                    (orderId, "kitchen", "Курьер назначен.");
            rabbitProducerService.sendMessage(objectMapper.writeValueAsString(messageForOrderService),
                    "notification");
            rabbitProducerService.sendMessage(objectMapper.writeValueAsString(messageForKitchenService),
                    "notification");
        }

        if (orderStatus.equals(OrderStatus.DELIVERY_COMPLETE)) {
            order.getCourier().setStatus(CourierStatus.COURIER_AVAILABLE);

            orderRepository.save(order);

            RabbitMessage messageForOrderService = new RabbitMessage
                    (orderId, "order", "Заказ доставлен.");
            rabbitProducerService.sendMessage(objectMapper.writeValueAsString(messageForOrderService),
                    "notification");
        }

        order.setStatus(orderStatus);
        orderRepository.save(order);

    }

    public void searchAvailableCourier(UUID orderId) {

        Courier courier = getNearestCourier(orderId);

        System.out.println("Ожидается ответ от курьера с id " + courier.getId());

    }

    private Courier getNearestCourier(UUID orderId) {

        String coordinatesOfRestaurant = orderRepository.findById(orderId).get()
                .getRestaurant().getCoordinates();


        return courierRepository.findAllByStatus(CourierStatus.COURIER_AVAILABLE).stream()
                .min(Comparator.comparingDouble(c ->
                        DistanceCalculator.calculateDistance(coordinatesOfRestaurant, c.getCoordinates())))
                .get();

    }


    private DeliveryDistances getDistances(UUID orderId, Long courierId) {

        String coordinatesOfCustomer = orderRepository.findById(orderId).get()
                .getCustomer().getCoordinates();

        String coordinatesOfRestaurant = orderRepository.findById(orderId).get()
                .getRestaurant().getCoordinates();

        String coordinatesOfCourier = courierRepository.findById(courierId).get()
                .getCoordinates();

        Double customerDistance = DistanceCalculator
                .calculateDistance(coordinatesOfRestaurant, coordinatesOfCustomer);

        Double restaurantDistance = DistanceCalculator
                .calculateDistance(coordinatesOfRestaurant, coordinatesOfCourier);

        return new DeliveryDistances(roundNumber(customerDistance), roundNumber(restaurantDistance));
    }

    private Double roundNumber(Double number) {
        return Math.round(number * 1000.0) / 1000.0;
    }

}
