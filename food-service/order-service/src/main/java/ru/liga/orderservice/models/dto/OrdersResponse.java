package ru.liga.orderservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrdersResponse {

    private List<OrderDTO> orders;

    private Integer pageIndex;
    private Integer pageCount;

}
