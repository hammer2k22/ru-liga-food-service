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
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.deliveryservice.feignClients.OrderServiceClient;
import ru.liga.deliveryservice.mappers.DeliveryMapper;
import ru.liga.deliveryservice.models.dto.DeliveriesResponse;
import ru.liga.deliveryservice.models.dto.DeliveryDTO;
import ru.liga.deliveryservice.models.dto.DeliveryDistances;

import java.util.Comparator;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final DeliveryMapper deliveryMapper;
    private final OrderServiceClient orderServiceClient;


    public DeliveriesResponse getDeliveriesResponseByStatus(int page, int size, String status) {

        Page<DeliveryDTO> deliveries = orderRepository
                .findAllByStatus(PageRequest.of(page, size), OrderStatus.valueOf(status))
                .map(order -> deliveryMapper.orderToDeliveryDTO(order, "some payment",
                        getDistances(order.getId())));

        return new DeliveriesResponse(deliveries.getContent(), deliveries.getNumber(), deliveries.getSize());
    }


    @Transactional
    public void updateOrderStatus(Map<String, String> requestBody, Long id) {

        Order order = orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("Order with id = " + id + " is not found"));

        String status = requestBody.get("orderAction");

        if (status.equals("COURIER_ACCEPTED")) {
            Courier courier = getNearestCourier(id);
            courier.setStatus(CourierStatus.COURIER_NOT_AVAILABLE);
            courierRepository.save(courier);
            order.setCourier(getNearestCourier(id));
            orderRepository.save(order);
        }

        if (status.equals("DELIVERY_COMPLETE")) {
            order.getCourier().setStatus(CourierStatus.COURIER_AVAILABLE);
            orderRepository.save(order);
        }

        orderServiceClient.updateOrder(id, status);
    }

    public void updateCourierStatus(Map<String, String> requestBody, Long courierId) {

        Courier courier = courierRepository.findById(courierId).orElseThrow(() ->
                new CourierNotFoundException("Order with id = " + courierId + " is not found"));

        String status = requestBody.get("orderAction");

        courier.setStatus(CourierStatus.valueOf(status));

        courierRepository.save(courier);
    }

    public void searchAvailableCourier(Long orderId) {

        Courier courier = getNearestCourier(orderId);

        System.out.println("Waiting courier with id" + courier.getId());

    }

    private Courier getNearestCourier(Long orderId) {

        String coordinatesOfRestaurant = orderRepository.findById(orderId).get()
                .getRestaurant().getCoordinates();


        return courierRepository.findAllByStatus("COURIER_ACTIVE").stream()
                .min(Comparator.comparingDouble(c ->
                        DistanceCalculator.calculateDistance(coordinatesOfRestaurant, c.getCoordinates())))
                .get();

    }


    private DeliveryDistances getDistances(Long orderId) {

        String coordinatesOfCustomer = orderRepository.findById(orderId).get()
                .getCustomer().getCoordinates();

        String coordinatesOfRestaurant = orderRepository.findById(orderId).get()
                .getRestaurant().getCoordinates();

        String coordinatesOfCourier = orderRepository.findById(orderId).get()
                .getCourier().getCoordinates();

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
