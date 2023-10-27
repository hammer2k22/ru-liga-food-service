package ru.liga.kitchenservice.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.models.OrderStatus;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.kitchenservice.feignClients.OrderServiceClient;
import ru.liga.kitchenservice.mappers.OrderMapper;
import ru.liga.kitchenservice.models.dto.OrderDTO;
import ru.liga.kitchenservice.models.dto.OrderResponse;
import ru.liga.kitchenservice.models.dto.OrdersResponse;
import ru.liga.kitchenservice.services.rabbitProducerService.RabbitProducerServiceImpl;

import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderServiceClient orderServiceClient;
    private final RabbitProducerServiceImpl rabbitProducerService;


    public OrderDTO getOrderById(Long id) throws JsonProcessingException {

        String response = orderServiceClient.getOrder(id);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(response, OrderDTO.class);
    }

    public OrdersResponse getOrdersResponseByStatus(int page, int size, String status) {

        Page<OrderDTO> orders = orderRepository
                .findAllByStatus(PageRequest.of(page, size), OrderStatus.valueOf(status))
                .map(orderMapper::orderToOrderDTO);

        return new OrdersResponse(orders.getContent(), orders.getNumber(), orders.getSize());

    }

    public OrderResponse updateOrderStatus(Long id, Map<String, String> orderStatus) {

        String status = orderStatus.get("orderAction");

        orderServiceClient.updateOrder(id, status);

        rabbitProducerService.sendMessage(id + "." + orderStatus, "notification");

        return new OrderResponse(id, status);

    }

}
