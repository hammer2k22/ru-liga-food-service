package ru.liga.common.util.exceptions;

public class CourierNotFoundException extends RuntimeException{

    public CourierNotFoundException(String message){
        super(message);
    }
}
