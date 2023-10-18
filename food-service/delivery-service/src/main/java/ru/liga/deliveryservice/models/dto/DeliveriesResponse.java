package ru.liga.deliveryservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DeliveriesResponse {

    private List<DeliveryDTO> deliveries;

    private Integer pageIndex;
    private Integer pageCount;

}
