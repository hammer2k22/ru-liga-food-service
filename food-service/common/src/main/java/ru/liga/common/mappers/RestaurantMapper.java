package ru.liga.common.mappers;

import org.mapstruct.Mapper;
import ru.liga.common.models.Restaurant;
import ru.liga.common.models.dto.RestaurantDTO;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant);

}






