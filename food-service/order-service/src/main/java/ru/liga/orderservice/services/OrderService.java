package ru.liga.orderservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.models.Order;
import ru.liga.common.models.OrderItem;
import ru.liga.common.models.OrderStatus;
import ru.liga.common.models.Restaurant;
import ru.liga.common.models.RestaurantMenuItem;
import ru.liga.common.repositories.CustomerRepository;
import ru.liga.common.repositories.OrderItemRepository;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.common.repositories.RestaurantMenuItemRepository;
import ru.liga.common.repositories.RestaurantRepository;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.common.util.exceptions.OrderStatusNotFoundException;
import ru.liga.common.util.exceptions.RestaurantMenuItemNotFoundException;
import ru.liga.common.util.exceptions.RestaurantNotFoundException;
import ru.liga.orderservice.mappers.OrderMapper;
import ru.liga.orderservice.models.dto.OrderCreateDTO;
import ru.liga.orderservice.models.dto.OrderCreateResponse;
import ru.liga.orderservice.models.dto.OrderDTO;
import ru.liga.orderservice.models.dto.OrderResponse;
import ru.liga.orderservice.models.dto.OrdersResponse;
import ru.liga.orderservice.services.rabbitProducerService.RabbitProducerServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


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


    public OrdersResponse getAllOrders(int page, int size) {

        Page<OrderDTO> orders = orderRepository.findAll(PageRequest.of(page, size))
                .map(orderMapper::orderToOrderDTO);

        return new OrdersResponse(orders.getContent(), orders.getNumber(), orders.getSize());
    }

    public OrderDTO getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id = " + id + " is not found"));

        return orderMapper.orderToOrderDTO(order);
    }
    @Transactional
    public OrderResponse updateOrderStatus(String orderStatus, Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id = " + id + " is not found"));

        boolean wrongFormatOrderStatus = Arrays.stream(OrderStatus.values())
                .map(Enum::toString)
                .noneMatch(status->status.equals(orderStatus));

        if(wrongFormatOrderStatus){
            throw new OrderStatusNotFoundException("Status " + orderStatus + " is not found");
        }

        order.setStatus(OrderStatus.valueOf(orderStatus));

        orderRepository.save(order);

        rabbitProducerService.sendMessage(id + "."+orderStatus, "notification");

        return new OrderResponse(id, orderStatus);
    }


    @Transactional
    public OrderResponse delete(Long id) {

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
