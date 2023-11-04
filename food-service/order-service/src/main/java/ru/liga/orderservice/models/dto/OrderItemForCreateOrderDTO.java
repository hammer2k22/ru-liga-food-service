package ru.liga.orderservice.models.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Schema(description = "Позиция для создания нового заказа")
public class OrderItemForCreateOrderDTO {

    private Long menuItemId;

    private Integer quantity;

}
