package ru.liga.kitchenservice.util.exceptions;

public class RestaurantMenuItemNotFoundException extends RuntimeException{

    public RestaurantMenuItemNotFoundException(String message){
        super(message);
    }
}
