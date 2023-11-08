package ru.liga.common.util.exceptions;

public class RestaurantMenuItemNotFoundException extends RuntimeException{

    public RestaurantMenuItemNotFoundException(String message){
        super(message);
    }
}
