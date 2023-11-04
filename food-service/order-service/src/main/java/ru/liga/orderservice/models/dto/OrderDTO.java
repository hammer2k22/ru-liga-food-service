package ru.liga.orderservice.models.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Schema(description = "Заказ")
public class OrderDTO {

    private Long id;

    private RestaurantDTO restaurant;

    private Timestamp timestamp;

    private List<OrderItemDTO> items;

}
