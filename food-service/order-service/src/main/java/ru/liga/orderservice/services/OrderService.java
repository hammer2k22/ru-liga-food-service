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
import ru.liga.common.util.exceptions.RestaurantMenuItemNotFoundException;
import ru.liga.common.util.exceptions.RestaurantNotFoundException;
import ru.liga.orderservice.mappers.OrderMapper;
import ru.liga.orderservice.models.dto.OrderCreateDTO;
import ru.liga.orderservice.models.dto.OrderCreateResponse;
import ru.liga.orderservice.models.dto.OrderDTO;
import ru.liga.orderservice.models.dto.OrdersResponse;

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

        return new OrderCreateResponse(order.getId(),"","");
    }


    public OrdersResponse getOrdersResponse(int page, int size) {

        Page<OrderDTO> orders = orderRepository.findAll(PageRequest.of(page, size))
                .map(orderMapper::orderToOrderDTO);

        return new OrdersResponse(orders.getContent(),orders.getNumber(),orders.getSize());
    }

    public OrderDTO getOrderDTOById(Long id) {

        Order order = orderRepository.findById(id).orElse(null);

        checkIfOrderIsNull(order,id);

        return orderMapper.orderToOrderDTO(order);
    }
    public void delete(Long id) {

        Order order = orderRepository.findById(id).orElse(null);
        checkIfOrderIsNull(order,id);
        order.setStatus(OrderStatus.CUSTOMER_CANCELLED);
    }

    private Long getOrderItemPrice(OrderItem orderItem){

        Long menuItemId = orderItem.getRestaurantMenuItem().getId();

        RestaurantMenuItem menuItem = restaurantMenuItemRepository
                .findById(menuItemId).orElse(null);
        checkIfRestaurantMenuItemIsNull(menuItem, menuItemId);

        Long menuItemPrice = menuItem.getPrice();
        Integer quantity = orderItem.getQuantity();
        return menuItemPrice*quantity;
    }

    private void checkIfOrderIsNull(Order order, Long id) {
        if (order == null) {
            throw new OrderNotFoundException("Order with id = "+id+" is not found");
        }
    }

    private void checkIfRestaurantIsNull(Order order) {
        Long restaurantId = order.getRestaurant().getId();
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if(restaurant.isEmpty()){
            throw new RestaurantNotFoundException("Restaurant with id = "+restaurantId+" is not found");
        }
    }

    private void checkIfRestaurantMenuItemIsNull(RestaurantMenuItem menuItem, Long menuItemId) {
        if (menuItem == null) {
            throw new RestaurantMenuItemNotFoundException
                    ("RestaurantMenuItem with id = "+menuItemId+" is not found");
        }
    }
}
