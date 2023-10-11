package ru.liga.orderservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private Long id;

    private Customer customer;

    private Restraunt restraunt;

    private String status;

    private Courier courier;

    private Long timestamp;

    private List<OrderItem> orderItems;

}
