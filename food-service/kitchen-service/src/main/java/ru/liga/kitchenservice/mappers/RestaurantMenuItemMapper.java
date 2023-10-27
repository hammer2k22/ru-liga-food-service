package ru.liga.kitchenservice.mappers;

import org.mapstruct.Mapper;
import ru.liga.common.models.Restaurant;
import ru.liga.common.models.RestaurantMenuItem;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemCreateDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemDTO;


@Mapper(componentModel = "spring")

public interface RestaurantMenuItemMapper {

    RestaurantMenuItem restaurantMenuItemCreateDTOToRestaurantMenuItem(RestaurantMenuItemCreateDTO
                                                                               restaurantMenuItemDTO);

    RestaurantMenuItemDTO restaurantMenuItemToRestaurantMenuItemDTO(RestaurantMenuItem
                                                                               restaurantMenuItem);

    default Restaurant mapRestaurantIdToRestaurant(Long restaurantId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }
}



