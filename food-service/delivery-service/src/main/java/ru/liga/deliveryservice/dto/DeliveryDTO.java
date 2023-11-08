package ru.liga.deliveryservice.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class DeliveryDTO {

    private UUID orderId;

    private RestaurantDTO restaurant;

    private CustomerDTO customer;

    private String payment;

}
