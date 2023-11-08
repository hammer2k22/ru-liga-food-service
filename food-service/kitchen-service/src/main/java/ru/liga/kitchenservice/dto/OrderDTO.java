package ru.liga.kitchenservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {

    private UUID id;

    private List<OrderItemDTO> items;

}
