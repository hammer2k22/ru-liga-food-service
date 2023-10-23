package ru.liga.kitchenservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantMenuItemUpdatedResponse {

    private Long restaurantMenuItemId;

    private Long price;
}
