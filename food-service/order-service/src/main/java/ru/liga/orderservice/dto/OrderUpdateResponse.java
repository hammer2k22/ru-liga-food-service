package ru.liga.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Ответ на создание нового заказа")
public class OrderUpdateResponse {

    private UUID id;
    private String secretPaymentUrl;
    private String estimatedTimeOfArrival;

}
