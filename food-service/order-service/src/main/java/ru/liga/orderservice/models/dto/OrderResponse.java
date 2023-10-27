package ru.liga.orderservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public  class OrderResponse {

    private Long orderId;
    private String orderStatus;

}
