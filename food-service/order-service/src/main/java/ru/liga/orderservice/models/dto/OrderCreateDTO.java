package ru.liga.orderservice.models.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderCreateDTO {

    private Long restrauntId;

    private List<OrderItemForCreateOrderDTO> orderItems;

}
