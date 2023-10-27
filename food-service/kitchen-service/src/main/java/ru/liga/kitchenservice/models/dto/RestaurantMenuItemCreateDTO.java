package ru.liga.kitchenservice.models.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantMenuItemCreateDTO {


    private String name;

    private Long price;

    private String image;

    private String description;

}
