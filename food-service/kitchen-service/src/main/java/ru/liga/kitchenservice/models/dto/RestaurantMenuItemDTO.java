package ru.liga.kitchenservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.liga.common.models.Restaurant;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantMenuItemDTO {

    private String name;

    private Long price;

    private String image;

    private String description;

}
