package ru.liga.orderservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class OrderCreateResponse {

    private Long id;
    private String secretPaymentUrl;
    private String estimatedTimeOfArrival;

}
