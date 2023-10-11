package ru.liga.orderservice.models.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderItemForUpdateOrderDTO {

    private Long menuItemId;

    private Integer quantity;

}
