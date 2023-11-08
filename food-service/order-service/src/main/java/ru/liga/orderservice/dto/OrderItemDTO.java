package ru.liga.orderservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Позиция в заказе")
public class OrderItemDTO {

    private String name;

    private BigDecimal price;

    private Integer quantity;

    private String description;

    private String image;

}
