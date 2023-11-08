package ru.liga.common.util.exceptions;

public class RestaurantStatusNotFoundException extends RuntimeException{

    public RestaurantStatusNotFoundException(String message){
        super(message);
    }
}
