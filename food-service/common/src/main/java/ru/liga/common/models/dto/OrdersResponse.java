package ru.liga.common.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrdersResponse {

    private List<OrderDTO> orders;

    private Integer pageIndex;
    private Integer pageCount;

}
