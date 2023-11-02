package ru.liga.kitchenservice.mappers;

import org.mapstruct.Mapper;
import ru.liga.common.models.Restaurant;
import ru.liga.kitchenservice.models.dto.RestaurantDTO;


@Mapper(componentModel = "spring")

public interface RestaurantMapper {

    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant);


}



