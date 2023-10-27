package ru.liga.kitchenservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantMenuItemCreatedResponse {

    private String restaurantName;

    private String menuItemName;

}
