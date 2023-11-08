package ru.liga.kitchenservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.liga.common.models.enums.OrderStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public  class KitchenResponse {

    private UUID orderId;
    private OrderStatus orderStatus;

}
