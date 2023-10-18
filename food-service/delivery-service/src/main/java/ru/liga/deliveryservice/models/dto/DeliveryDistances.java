package ru.liga.deliveryservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDistances {

    private Long customerDistance;
    private Long restaurantDistance;
}
