package ru.liga.deliveryservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.common.models.Order;
import ru.liga.deliveryservice.models.dto.DeliveryDTO;
import ru.liga.deliveryservice.models.dto.DeliveryDistances;



@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "distances.customerDistance", target = "customer.distance")
    @Mapping(source = "distances.restaurantDistance", target = "restaurant.distance")
    @Mapping(source = "order.restaurant.address", target = "customer.address")
    @Mapping(source = "order.customer.address", target = "restaurant.address")
    DeliveryDTO orderToDeliveryDTO(Order order, String payment, DeliveryDistances distances);

}







