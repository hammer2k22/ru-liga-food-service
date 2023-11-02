package ru.liga.kitchenservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantsResponse {

    private List<RestaurantDTO> restaurants;

    private Integer pageIndex;
    private Integer pageCount;

}
