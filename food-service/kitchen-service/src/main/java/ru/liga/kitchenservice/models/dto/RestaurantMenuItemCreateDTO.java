package ru.liga.kitchenservice.models.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantMenuItemCreateDTO {


    private String name;

    private BigDecimal price;

    private String image;

    private String description;

}
