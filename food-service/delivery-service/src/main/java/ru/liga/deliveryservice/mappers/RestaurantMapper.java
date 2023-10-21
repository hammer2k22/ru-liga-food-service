package ru.liga.deliveryservice.mappers;


import org.mapstruct.Mapper;
import ru.liga.deliveryservice.models.Restaurant;
import ru.liga.deliveryservice.models.dto.RestaurantDTO;


@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant);
}







