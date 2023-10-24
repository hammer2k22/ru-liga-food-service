package ru.liga.deliveryservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.models.Order;
import ru.liga.common.models.OrderStatus;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.common.util.DistanceCalculator;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.deliveryservice.mappers.DeliveryMapper;
import ru.liga.deliveryservice.models.dto.DeliveriesResponse;
import ru.liga.deliveryservice.models.dto.DeliveryDTO;
import ru.liga.deliveryservice.models.dto.DeliveryDistances;


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

        Double customerDistance = DistanceCalculator
                .calculateDistance(coordinatesOfCourier, coordinatesOfCustomer);

        Double restaurantDistance = DistanceCalculator
                .calculateDistance(coordinatesOfCourier, coordinatesOfRestaurant);

        return new DeliveryDistances(String.format("%.3f", customerDistance),
                String.format("%.3f", restaurantDistance));
    }

    private void checkIfOrderIsNull(Order order, Long id) {
        if (order == null) {
            throw new OrderNotFoundException("Order with id = " + id + " is not found");
        }
    }
}
