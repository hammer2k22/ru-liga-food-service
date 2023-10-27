package ru.liga.deliveryservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryDistances {

    private Double customerDistance;

    private Double restaurantDistance;
}
