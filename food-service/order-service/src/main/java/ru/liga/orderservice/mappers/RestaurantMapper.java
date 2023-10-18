package ru.liga.orderservice.mappers;

import org.mapstruct.Mapper;
import ru.liga.orderservice.models.Restaurant;
import ru.liga.orderservice.models.dto.RestaurantDTO;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant);

}






