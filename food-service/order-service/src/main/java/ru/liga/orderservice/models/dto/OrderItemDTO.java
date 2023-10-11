package ru.liga.orderservice.models.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

    private Long price;

    private Integer quantity;

    private RestrauntMenuItemForOrderItemDTO restrauntMenuItem;


}
