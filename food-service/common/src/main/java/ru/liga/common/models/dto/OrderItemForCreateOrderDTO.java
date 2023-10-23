package ru.liga.common.models.dto;


import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class OrderItemForCreateOrderDTO {

    private Long menuItemId;

    private Integer quantity;

}
