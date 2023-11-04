package ru.liga.orderservice.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Ответ на создание нового заказа")
public class OrderCreateResponse {

    private Long id;
    private String secretPaymentUrl;
    private String estimatedTimeOfArrival;

}
