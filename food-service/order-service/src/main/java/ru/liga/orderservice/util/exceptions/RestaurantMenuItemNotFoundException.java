package ru.liga.orderservice.util.exceptions;

public class RestaurantMenuItemNotFoundException extends RuntimeException{

    public RestaurantMenuItemNotFoundException(String message){
        super(message);
    }
}
