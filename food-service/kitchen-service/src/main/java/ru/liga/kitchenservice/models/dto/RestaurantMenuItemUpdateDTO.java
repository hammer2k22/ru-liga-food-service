package ru.liga.kitchenservice.models.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantMenuItemUpdateDTO {

    private BigDecimal price;

}
