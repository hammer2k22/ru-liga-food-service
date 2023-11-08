package ru.liga.kitchenservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.common.models.OrderItem;
import ru.liga.kitchenservice.dto.OrderItemDTO;


@Mapper(componentModel = "spring")

public interface OrderItemMapper {

    @Mapping(source = "restaurantMenuItem.name", target = "name")
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);

}



