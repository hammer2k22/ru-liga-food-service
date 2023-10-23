package ru.liga.common.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.common.models.OrderItem;
import ru.liga.common.models.RestaurantMenuItem;
import ru.liga.common.models.dto.OrderItemDTO;
import ru.liga.common.models.dto.OrderItemForCreateOrderDTO;

@Mapper(componentModel = "spring")

public interface OrderItemMapper {
    @Mapping(source = "orderItem.restaurantMenuItem.image", target = "image")
    @Mapping(source = "orderItem.restaurantMenuItem.description", target = "description")
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);

    @Mapping(source = "menuItemId", target = "restaurantMenuItem")
    OrderItem orderItemForCreateOrderToOrderItem(OrderItemForCreateOrderDTO orderItemForCreateOrderDTO);

    default RestaurantMenuItem mapRestaurantMenuItemIdToRestaurant(Long restaurantMenuItemId) {
        RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem();
        restaurantMenuItem.setId(restaurantMenuItemId);
        return restaurantMenuItem;
    }
}



