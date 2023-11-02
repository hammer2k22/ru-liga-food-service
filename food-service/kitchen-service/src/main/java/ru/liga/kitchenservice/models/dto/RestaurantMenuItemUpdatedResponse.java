package ru.liga.kitchenservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantMenuItemUpdatedResponse {

    private String restaurantMenuItemName;

    private BigDecimal price;
}
