package ru.liga.orderservice.models.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDTO {

    private String name;

    private BigDecimal price;

    private Integer quantity;

    private String description;

    private String image;

}
