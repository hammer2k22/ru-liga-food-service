package ru.liga.common.util.exceptions;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String message){
        super(message);
    }
}
