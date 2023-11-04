package ru.liga.orderservice.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Ресторан")
public class RestaurantDTO {

    private String name;

}
