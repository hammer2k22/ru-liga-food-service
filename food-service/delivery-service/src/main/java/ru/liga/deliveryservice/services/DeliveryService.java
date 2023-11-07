package ru.liga.deliveryservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.models.Courier;
import ru.liga.common.models.CourierStatus;
import ru.liga.common.models.Order;
import ru.liga.common.models.OrderStatus;
import ru.liga.common.repositories.CourierRepository;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.common.util.DistanceCalculator;
import ru.liga.common.util.exceptions.CourierNotFoundException;
import ru.liga.common.util.exceptions.CourierStatusNotFoundException;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.common.util.exceptions.OrderStatusNotFoundException;
import ru.liga.deliveryservice.feignClients.OrderServiceClient;
import ru.liga.deliveryservice.mappers.DeliveryMapper;
import ru.liga.deliveryservice.models.dto.DeliveriesResponse;
import ru.liga.deliveryservice.models.dto.DeliveryDTO;
import ru.liga.deliveryservice.models.dto.DeliveryDistances;
import ru.liga.deliveryservice.services.rabbitProducerService.RabbitProducerServiceImpl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final DeliveryMapper deliveryMapper;
    private final RabbitProducerServiceImpl rabbitProducerService;


    public DeliveriesResponse getDeliveriesResponseByStatus(int page, int size, String status) {

        Courier courier = courierRepository.findAll().get(0);  /*Заглушка*/

        Page<DeliveryDTO> deliveries = orderRepository
                .findAllByStatus(PageRequest.of(page, size), OrderStatus.valueOf(status))
                .map(order -> deliveryMapper.orderToDeliveryDTO(order, "some payment",
                        getDistances(order.getId(), courier.getId())));

        return new DeliveriesResponse(deliveries.getContent(), deliveries.getNumber(), deliveries.getSize());
    }
    @Transactional
    public void updateOrderStatus(String orderStatus, Long id) {

        Order order = orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("Order with id = " + id + " is not found"));

        boolean wrongFormatOrderStatus = Arrays.stream(OrderStatus.values())
                .map(Enum::toString)
                .noneMatch(status->status.equals(orderStatus));

        if(wrongFormatOrderStatus){
            throw new OrderStatusNotFoundException("Status " + orderStatus + " is not found");
        }


        if (orderStatus.equals("COURIER_ACCEPTED")) {
            Courier courier = getNearestCourier(id);
            courier.setStatus(CourierStatus.COURIER_NOT_AVAILABLE);
            courierRepository.save(courier);
            order.setCourier(getNearestCourier(id));
            orderRepository.save(order);
        }

        if (orderStatus.equals("DELIVERY_COMPLETE")) {
            order.getCourier().setStatus(CourierStatus.COURIER_AVAILABLE);
            orderRepository.save(order);
        }

        order.setStatus(OrderStatus.valueOf(orderStatus));
        orderRepository.save(order);

        rabbitProducerService.sendMessage(id + "."+orderStatus, "notification");
    }

    @Transactional
    public void updateCourierStatus(String courierStatus, Long courierId) {

        Courier courier = courierRepository.findById(courierId).orElseThrow(() ->
                new CourierNotFoundException("Order with id = " + courierId + " is not found"));

        boolean wrongFormatCourierStatus = Arrays.stream(CourierStatus.values())
                .map(Enum::toString)
                .noneMatch(status->status.equals(courierStatus));

        if(wrongFormatCourierStatus){
            throw new CourierStatusNotFoundException("Status " + courierStatus + " is not found");
        }

        courier.setStatus(CourierStatus.valueOf(courierStatus));

        courierRepository.save(courier);
    }

    public void searchAvailableCourier(Long orderId) {

        Courier courier = getNearestCourier(orderId);

        System.out.println("Waiting courier with id " + courier.getId());

    }

    private Courier getNearestCourier(Long orderId) {

        String coordinatesOfRestaurant = orderRepository.findById(orderId).get()
                .getRestaurant().getCoordinates();


        return courierRepository.findAllByStatus(CourierStatus.COURIER_AVAILABLE).stream()
                .min(Comparator.comparingDouble(c ->
                        DistanceCalculator.calculateDistance(coordinatesOfRestaurant, c.getCoordinates())))
                .get();

    }


    private DeliveryDistances getDistances(Long orderId, Long courierId) {

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
