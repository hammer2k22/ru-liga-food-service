package ru.liga.deliveryservice.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.deliveryservice.models.Order;
import ru.liga.deliveryservice.models.dto.DeliveryDistances;
import ru.liga.deliveryservice.models.dto.DeliveryDTO;



@Mapper(componentModel = "spring", uses = {CustomerMapper.class, RestaurantMapper.class})
public interface DeliveryMapper {
    @Mapping(source = "order.id", target = "orderId")
    DeliveryDTO orderToDeliveryDTO(Order order, String payment, @Context DeliveryDistances distances);

}







