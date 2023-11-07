package ru.liga.kitchenservice.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.models.Order;
import ru.liga.common.models.OrderStatus;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.common.util.exceptions.OrderStatusNotFoundException;
import ru.liga.kitchenservice.feignClients.OrderServiceClient;
import ru.liga.kitchenservice.mappers.OrderMapper;
import ru.liga.kitchenservice.models.dto.OrderDTO;
import ru.liga.kitchenservice.models.dto.OrderResponse;
import ru.liga.kitchenservice.models.dto.OrdersResponse;
import ru.liga.kitchenservice.services.rabbitProducerService.RabbitProducerServiceImpl;

import java.util.Arrays;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RabbitProducerServiceImpl rabbitProducerService;


    public OrderDTO getOrderById(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("Order with id " + id + " is not found"));

        return orderMapper.orderToOrderDTO(order);
    }

    public OrdersResponse getOrdersResponseByStatus(int page, int size, String status) {

        Page<OrderDTO> orders = orderRepository
                .findAllByStatus(PageRequest.of(page, size), OrderStatus.valueOf(status))
                .map(orderMapper::orderToOrderDTO);

        return new OrdersResponse(orders.getContent(), orders.getNumber(), orders.getSize());

    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, String orderStatus) {

        Order order = orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("Order with id " + id + " is not found"));

        boolean wrongFormatOrderStatus = Arrays.stream(OrderStatus.values())
                .map(Enum::toString)
                .noneMatch(status->status.equals(orderStatus));

        if(wrongFormatOrderStatus){
            throw new OrderStatusNotFoundException("Status " + orderStatus + " is not found");
        }

        order.setStatus(OrderStatus.valueOf(orderStatus));

        rabbitProducerService.sendMessage(id + "." + orderStatus, "notification");

        return new OrderResponse(id, orderStatus);

    }

}
