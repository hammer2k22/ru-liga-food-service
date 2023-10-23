package ru.liga.common.models.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderCreateDTO {

    private Long restaurantId;

    private List<OrderItemForCreateOrderDTO> menuItems;

}
