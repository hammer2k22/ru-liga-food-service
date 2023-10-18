package ru.liga.deliveryservice.models.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DeliveryDTO {

    private Long orderId;

    private RestaurantDTO restaurant;

    private CustomerDTO customer;

    private String payment;

}
