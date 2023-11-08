package ru.liga.orderservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Schema(description = "Запрос для создания нового заказа")
public class OrderCreateDTO {

    private Long restaurantId;

    private List<OrderItemForCreateOrderDTO> menuItems;

}
