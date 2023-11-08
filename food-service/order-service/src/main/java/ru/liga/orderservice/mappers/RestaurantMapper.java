package ru.liga.orderservice.mappers;

import org.mapstruct.Mapper;
import ru.liga.common.models.Restaurant;
import ru.liga.orderservice.dto.RestaurantDTO;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant);

}






