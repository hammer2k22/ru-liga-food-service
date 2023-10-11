package ru.liga.orderservice.models.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class OrderDTO {

    private Long id;

    private RestrauntDTO restraunt;

    private Long timestamp;

    private List<OrderItemDTO> items;

}
