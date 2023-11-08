package ru.liga.orderservice.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.dto.RabbitMessage;
import ru.liga.common.models.Order;
import ru.liga.common.models.OrderItem;
import ru.liga.common.models.Restaurant;
import ru.liga.common.models.RestaurantMenuItem;
import ru.liga.common.models.enums.OrderStatus;
import ru.liga.common.repositories.CustomerRepository;
import ru.liga.common.repositories.OrderItemRepository;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.common.repositories.RestaurantMenuItemRepository;
import ru.liga.common.repositories.RestaurantRepository;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.common.util.exceptions.RestaurantMenuItemNotFoundException;
import ru.liga.common.util.exceptions.RestaurantNotFoundException;
import ru.liga.orderservice.dto.OrderCreateDTO;
import ru.liga.orderservice.dto.OrderCreateResponse;
import ru.liga.orderservice.dto.OrderDTO;
import ru.liga.orderservice.dto.OrderResponse;
import ru.liga.orderservice.dto.OrderUpdateDTO;
import ru.liga.orderservice.dto.OrderUpdateResponse;
import ru.liga.orderservice.dto.OrdersResponse;
import ru.liga.orderservice.mappers.OrderMapper;
import ru.liga.orderservice.services.rabbitProducerService.RabbitProducerServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderMapper orderMapper;
    private final RabbitProducerServiceImpl rabbitProducerService;
    private final ObjectMapper objectMapper;


    @Transactional
    public OrderCreateResponse createNewOrder(OrderCreateDTO orderCreateDTO) {

        Order order = orderMapper.orderCreateDTOtoOrder(orderCreateDTO);
        checkIfRestaurantIsNull(order);
        order.setCustomer(customerRepository.findAll().get(0));    /*Для проверки работоспособности*/
        order.setStatus(OrderStatus.CUSTOMER_CREATED);
        orderRepository.save(order);

        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderItems.forEach(orderItem -> orderItem.setPrice(getOrderItemPrice(orderItem)));
        orderItemRepository.saveAll(orderItems);

        return new OrderCreateResponse(order.getId(), "some payment", "some time");
    }

    public OrderDTO getOrderById(UUID id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id = " + id + " is not found"));

        return orderMapper.orderToOrderDTO(order);
    }


    public OrdersResponse getAllOrders(int page, int size) {

        Page<OrderDTO> orders = orderRepository.findAll(PageRequest.of(page, size))
                .map(orderMapper::orderToOrderDTO);

        return new OrdersResponse(orders.getContent(), orders.getNumber(), orders.getSize());
    }

    @Transactional
    @SneakyThrows
    public OrderResponse updateOrderStatus(OrderStatus orderStatus, UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException
                        ("Order with orderId = " + orderId + " is not found"));

        order.setStatus(orderStatus);

        orderRepository.save(order);

        if (orderStatus.equals(OrderStatus.CUSTOMER_PAID)) {
            RabbitMessage rabbitMessage = new RabbitMessage
                    (orderId, "kitchen", "Новый заказ  ожидает подтверждения.");

            rabbitProducerService.sendMessage(objectMapper.writeValueAsString(rabbitMessage),
                    "notification");
        }

        return new OrderResponse(orderId, orderStatus.toString());
    }

    @Transactional
    public OrderUpdateResponse updateOrder(UUID orderId, OrderUpdateDTO orderUpdateDTO) {

        Order orderToBeUpdate = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id = " + orderId + " is not found"));

        Order updatedOrder = orderMapper.orderUpdateDTOtoOrder(orderUpdateDTO);

        orderToBeUpdate.setRestaurant(updatedOrder.getRestaurant());

        orderRepository.save(orderToBeUpdate);

        orderItemRepository.deleteAll(orderToBeUpdate.getOrderItems());

        List<OrderItem> newOrderItems = updatedOrder.getOrderItems();
        newOrderItems.forEach(orderItem -> orderItem.setOrder(orderToBeUpdate));
        newOrderItems.forEach(orderItem -> orderItem.setPrice(getOrderItemPrice(orderItem)));

        orderItemRepository.saveAll(newOrderItems);

        return new OrderUpdateResponse(orderToBeUpdate.getId(), "some payment", "some time");

    }

    @Transactional
    public OrderResponse delete(UUID id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id = " + id + " is not found"));

        order.setStatus(OrderStatus.CUSTOMER_CANCELLED);

        return new OrderResponse(id, OrderStatus.CUSTOMER_CANCELLED.toString());
    }

    private BigDecimal getOrderItemPrice(OrderItem orderItem) {

        Long menuItemId = orderItem.getRestaurantMenuItem().getId();

        RestaurantMenuItem menuItem = restaurantMenuItemRepository
                .findById(menuItemId).orElseThrow(() -> new RestaurantMenuItemNotFoundException
                        ("RestaurantMenuItem with id = " + menuItemId + " is not found"));

        BigDecimal menuItemPrice = menuItem.getPrice();
        Integer quantity = orderItem.getQuantity();
        return menuItemPrice.multiply(BigDecimal.valueOf(quantity));
    }

    private void checkIfRestaurantIsNull(Order order) {
        Long restaurantId = order.getRestaurant().getId();
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant with id = " + restaurantId + " is not found");
        }
    }

}
