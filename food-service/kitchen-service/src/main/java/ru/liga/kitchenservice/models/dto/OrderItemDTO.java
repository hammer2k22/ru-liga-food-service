package ru.liga.kitchenservice.models.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

    private Integer quantity;

    private Long menuItemId;

}
