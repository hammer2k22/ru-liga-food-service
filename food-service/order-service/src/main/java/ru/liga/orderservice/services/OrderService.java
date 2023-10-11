package ru.liga.orderservice.services;


import org.springframework.stereotype.Service;
import ru.liga.orderservice.util.exceptions.OrderNotFoundException;
import ru.liga.orderservice.models.Order;

import java.util.List;


@Service
public class OrderService {



    public void create(Order order) {

        order.setTimestamp(System.currentTimeMillis());

    }

    public List<Order> getAllOrders(){

        return List.of(new Order());
    }

    public Order getById(Long id) {

        Order order = new Order();
        checkIfOrderIsNull(order);
        return order;
    }

    public void update(Long id, Order order) {


    }



    public void delete(Long id) {


    }

    private void checkIfOrderIsNull(Order order) {
        if (order == null) {
            throw new OrderNotFoundException("There is no order with this Id");
        }
    }
}
