package ru.liga.common.util.exceptions;

public class CourierStatusNotFoundException extends RuntimeException{

    public CourierStatusNotFoundException(String message){
        super(message);
    }
}
