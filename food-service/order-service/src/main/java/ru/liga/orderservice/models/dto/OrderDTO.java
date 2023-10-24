package ru.liga.orderservice.models.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private Long id;

    private RestaurantDTO restaurant;

    private Timestamp timestamp;

    private List<OrderItemDTO> items;

}
