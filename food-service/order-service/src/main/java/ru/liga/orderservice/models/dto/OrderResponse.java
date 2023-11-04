package ru.liga.orderservice.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Обновленный статус заказа")
public  class OrderResponse {

    private Long orderId;
    private String orderStatus;

}
