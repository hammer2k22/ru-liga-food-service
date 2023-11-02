package ru.liga.kitchenservice.models.dto;

import lombok.Getter;
import lombok.Setter;
import ru.liga.common.models.RestaurantStatus;


@Getter
@Setter
public class RestaurantDTO {

    private Long id;

    private String name;

    private String address;

    private RestaurantStatus status;

}
