package ru.liga.orderservice.models.dto;

import lombok.Getter;
import lombok.Setter;
import ru.liga.orderservice.models.Restraunt;

import java.util.List;

@Getter
@Setter
public class OrderToBeUpdateDTO {

    private Long restrauntId;

    private List<OrderItemForUpdateOrderDTO> orderItems;
}
