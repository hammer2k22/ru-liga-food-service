package ru.liga.deliveryservice.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.deliveryservice.models.Restaurant;
import ru.liga.deliveryservice.models.dto.DeliveryDistances;
import ru.liga.deliveryservice.models.dto.RestaurantDTO;


@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    @Mapping(source = "distances.restaurantDistance", target = "distance")
    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant, DeliveryDistances distances);
}







