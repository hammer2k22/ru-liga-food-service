package ru.liga.orderservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Заказ")
public class OrderDTO {

    private UUID id;

    private RestaurantDTO restaurant;

    private Timestamp timestamp;

    private List<OrderItemDTO> items;

}
