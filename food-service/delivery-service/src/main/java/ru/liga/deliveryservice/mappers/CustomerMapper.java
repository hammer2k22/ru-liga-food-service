package ru.liga.deliveryservice.mappers;

import org.mapstruct.Mapper;
import ru.liga.common.models.Customer;
import ru.liga.deliveryservice.models.dto.CustomerDTO;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO customerToCustomerDTO(Customer customer);
}







