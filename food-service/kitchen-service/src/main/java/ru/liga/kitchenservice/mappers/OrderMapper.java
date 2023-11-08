package ru.liga.kitchenservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.common.models.Order;
import ru.liga.kitchenservice.dto.OrderDTO;


@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "orderItems", target = "items")
    OrderDTO orderToOrderDTO(Order order);

}







