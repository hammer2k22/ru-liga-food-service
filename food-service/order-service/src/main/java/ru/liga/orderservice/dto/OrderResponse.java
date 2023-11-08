package ru.liga.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Schema(description = "Обновленный статус заказа")
public  class OrderResponse {

    private UUID orderId;
    private String orderStatus;

}
