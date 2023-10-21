package ru.liga.deliveryservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.deliveryservice.mappers.DeliveryMapper;
import ru.liga.deliveryservice.models.Order;
import ru.liga.deliveryservice.models.OrderStatus;
import ru.liga.deliveryservice.models.dto.DeliveriesResponse;
import ru.liga.deliveryservice.models.dto.DeliveryDTO;
import ru.liga.deliveryservice.models.dto.DeliveryDistances;
import ru.liga.deliveryservice.repositories.OrderRepository;
import ru.liga.deliveryservice.util.DistanceCalculator;
import ru.liga.deliveryservice.util.exceptions.OrderNotFoundException;

import java.text.DecimalFormat;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final DeliveryMapper deliveryMapper;


    public DeliveriesResponse getDeliveriesResponseByStatus(int page, int size, String status) {

        Page<DeliveryDTO> deliveries = orderRepository
                .findAllByStatus(PageRequest.of(page, size), OrderStatus.valueOf(status))
                .map(order -> deliveryMapper.orderToDeliveryDTO(order, "some payment",
                        getDistances(order.getId())));

        return new DeliveriesResponse(deliveries.getContent(), deliveries.getNumber(), deliveries.getSize());
    }


    @Transactional
    public void updateOrderStatus(Map<String, String> requestBody, Long id) {

        Order order = orderRepository.findById(id).orElse(null);
        checkIfOrderIsNull(order, id);
        String status = requestBody.get("orderAction");
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        order.setStatus(orderStatus);

        orderRepository.save(order);

    }

    private DeliveryDistances getDistances(Long orderId) {

        String coordinatesOfCustomer = orderRepository.findById(orderId).get()
                .getCustomer().getCoordinates();

        String coordinatesOfRestaurant = orderRepository.findById(orderId).get()
                .getRestaurant().getCoordinates();

        String coordinatesOfCourier = orderRepository.findById(orderId).get()
                .getCourier().getCoordinates();

        String customerDistance = String.valueOf(round(DistanceCalculator
                .calculateDistance(coordinatesOfCourier, coordinatesOfCustomer)));

        String restaurantDistance = String.valueOf(round(DistanceCalculator
                .calculateDistance(coordinatesOfCourier, coordinatesOfRestaurant)));

        return new DeliveryDistances(customerDistance + " km",
                restaurantDistance + " km");
    }

    private Double round(Double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String roundedValue = decimalFormat.format(value);
        return Double.valueOf(roundedValue.replace(",", "."));
    }

    private void checkIfOrderIsNull(Order order, Long id) {
        if (order == null) {
            throw new OrderNotFoundException("Order with id = " + id + " is not found");
        }
    }
}
