package ru.liga.orderservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestrauntMenuItem {

    private Long id;

    private Restraunt restraunt;

    private String name;

    private Long price;

    private String image;  /*Не знаю, какой тип данных должен быть у поля)*/

    private String description;
}
