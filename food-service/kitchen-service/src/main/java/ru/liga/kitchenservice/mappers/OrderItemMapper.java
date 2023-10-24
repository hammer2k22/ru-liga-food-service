package ru.liga.kitchenservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.common.models.OrderItem;
import ru.liga.kitchenservice.models.dto.OrderItemDTO;


@Mapper(componentModel = "spring")

public interface OrderItemMapper {

    @Mapping(source = "restaurantMenuItem.id", target = "menuItemId")
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);

}



