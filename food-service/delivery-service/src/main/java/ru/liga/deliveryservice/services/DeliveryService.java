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
import ru.liga.deliveryservice.util.exceptions.OrderNotFoundException;

import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final DeliveryMapper deliveryMapper;


    public DeliveriesResponse getDeliveriesResponseByStatus(int page, int size, String status) {

        DeliveryDistances distances = new DeliveryDistances(10L,10L);

        Page<DeliveryDTO> deliveries = orderRepository
                .findAllByStatus(PageRequest.of(page, size), OrderStatus.valueOf(status))
                .map(order -> deliveryMapper.orderToDeliveryDTO(order, "", distances));

        return new DeliveriesResponse(deliveries.getContent(), deliveries.getNumber(), deliveries.getSize());
    }

    @Transactional
    public void updateOrderStatus(Map<String,String> requestBody, Long id) {

        Order order = orderRepository.findById(id).orElse(null);
        checkIfOrderIsNull(order,id);
        String status = requestBody.get("orderAction");
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        order.setStatus(orderStatus);

        orderRepository.save(order);
        
    }

    private void checkIfOrderIsNull(Order order, Long id) {
        if (order == null) {
            throw new OrderNotFoundException("Order with id = "+id+" is not found");
        }
    }
}
