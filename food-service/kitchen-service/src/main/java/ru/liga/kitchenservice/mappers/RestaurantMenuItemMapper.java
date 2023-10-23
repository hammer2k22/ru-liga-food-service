package ru.liga.kitchenservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.kitchenservice.models.Restaurant;
import ru.liga.kitchenservice.models.RestaurantMenuItem;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemCreateDTO;


@Mapper(componentModel = "spring")

public interface RestaurantMenuItemMapper {


    @Mapping(source = "restaurantId",target = "restaurant")
    RestaurantMenuItem restaurantMenuItemCreateDTOToRestaurantMenuItem(RestaurantMenuItemCreateDTO
                                                                               restaurantMenuItemDTO);

    default Restaurant mapRestaurantIdToRestaurant(Long restaurantId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }
}



