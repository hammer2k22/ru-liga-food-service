package ru.liga.kitchenservice.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.dto.RabbitMessage;
import ru.liga.common.models.Order;
import ru.liga.common.models.enums.OrderStatus;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.kitchenservice.mappers.OrderMapper;
import ru.liga.kitchenservice.dto.KitchenResponse;
import ru.liga.kitchenservice.dto.OrderDTO;
import ru.liga.kitchenservice.services.rabbitProducerService.RabbitProducerServiceImpl;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KitchenService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final RabbitProducerServiceImpl rabbitProducerService;
    private final ObjectMapper objectMapper;

    @Transactional
    @SneakyThrows
    public KitchenResponse updateOrderStatus(UUID orderId, OrderStatus orderStatus) {

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException("Order with id = " + orderId + " is not found"));

        order.setStatus(orderStatus);

        orderRepository.save(order);

        if (orderStatus.equals(OrderStatus.KITCHEN_ACCEPTED)) {
            RabbitMessage messageForOrderService = new RabbitMessage
                    (orderId, "order", "Кухня приняла заказ.");

            rabbitProducerService.sendMessage(objectMapper.writeValueAsString(messageForOrderService),
                    "notification");
        }

        if (orderStatus.equals(OrderStatus.KITCHEN_DECLINED)) {
            RabbitMessage messageForOrderService = new RabbitMessage
                    (orderId, "order", "Кухня отклонила заказ.");

            rabbitProducerService.sendMessage(objectMapper.writeValueAsString(messageForOrderService),
                    "notification");
        }

        if (orderStatus.equals(OrderStatus.KITCHEN_READIED)) {
            RabbitMessage messageForOrderService = new RabbitMessage
                    (orderId, "order", "Заказ готов. Ожидается курьер.");

            RabbitMessage messageForDeliveryService = new RabbitMessage
                    (orderId, "delivery", "Заказ готов. Ожидается курьер.");

            rabbitProducerService.sendMessage(objectMapper.writeValueAsString(messageForOrderService),
                    "notification");

            rabbitProducerService.sendMessage(objectMapper.writeValueAsString(messageForDeliveryService),
                    "notification");
        }

        return new KitchenResponse(orderId,orderStatus);
    }

    public OrderDTO getOrderById(UUID id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id = " + id + " is not found"));

        return orderMapper.orderToOrderDTO(order);
    }

}
