package ru.liga.deliveryservice.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.deliveryservice.models.Customer;
import ru.liga.deliveryservice.models.dto.CustomerDTO;
import ru.liga.deliveryservice.models.dto.DeliveryDistances;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(source = "distances.customerDistance", target = "distance")
    CustomerDTO customerToCustomerDTO(Customer customer, DeliveryDistances distances);
}







