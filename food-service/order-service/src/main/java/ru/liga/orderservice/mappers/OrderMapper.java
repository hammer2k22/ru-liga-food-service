package ru.liga.orderservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.common.models.Order;
import ru.liga.common.models.Restaurant;
import ru.liga.orderservice.models.dto.OrderCreateDTO;
import ru.liga.orderservice.models.dto.OrderDTO;


@Mapper(componentModel = "spring", uses = {RestaurantMapper.class, OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "orderItems", target = "items")
    OrderDTO orderToOrderDTO(Order order);

    @Mapping(source = "restaurantId", target = "restaurant")
    @Mapping(source = "menuItems", target = "orderItems")
    Order orderCreateDTOtoOrder(OrderCreateDTO orderCreateDTO);

    default Restaurant mapRestaurantIdToRestaurant(Long restaurantId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }
}







