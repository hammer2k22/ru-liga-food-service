package ru.liga.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryDistances {

    private Double customerDistance;

    private Double restaurantDistance;
}
