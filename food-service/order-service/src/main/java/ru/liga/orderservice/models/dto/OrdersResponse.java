package ru.liga.orderservice.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "История заказов")
public class OrdersResponse {

    private List<OrderDTO> orders;

    private Integer pageIndex;
    private Integer pageCount;

}
