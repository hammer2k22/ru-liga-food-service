package ru.liga.kitchenservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantMenuItemsResponse {

    private List<RestaurantMenuItemDTO> menuItems;

    private Integer pageIndex;
    private Integer pageCount;

}
