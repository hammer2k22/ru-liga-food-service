package ru.liga.orderservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.common.models.Order;
import ru.liga.common.models.Restaurant;
import ru.liga.orderservice.dto.OrderCreateDTO;
import ru.liga.orderservice.dto.OrderDTO;
import ru.liga.orderservice.dto.OrderUpdateDTO;


@Mapper(componentModel = "spring", uses = {RestaurantMapper.class, OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "orderItems", target = "items")
    OrderDTO orderToOrderDTO(Order order);

    @Mapping(source = "restaurantId", target = "restaurant")
    @Mapping(source = "menuItems", target = "orderItems")
    Order orderCreateDTOtoOrder(OrderCreateDTO orderCreateDTO);

    @Mapping(source = "restaurantId", target = "restaurant")
    @Mapping(source = "menuItems", target = "orderItems")
    Order orderUpdateDTOtoOrder(OrderUpdateDTO orderUpdateDTO);

    default Restaurant mapRestaurantIdToRestaurant(Long restaurantId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }
}







