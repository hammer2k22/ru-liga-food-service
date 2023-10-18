package ru.liga.orderservice.util.exceptions;

public class RestaurantNotFoundException extends RuntimeException{

    public RestaurantNotFoundException(String message){
        super(message);
    }
}
