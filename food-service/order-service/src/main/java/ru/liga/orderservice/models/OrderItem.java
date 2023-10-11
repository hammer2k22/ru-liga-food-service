package ru.liga.orderservice.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {

    private Long id;

    private Order order;

    private RestrauntMenuItem restrauntMenuItem;

    private Long price;

    private Integer quantity;

}
