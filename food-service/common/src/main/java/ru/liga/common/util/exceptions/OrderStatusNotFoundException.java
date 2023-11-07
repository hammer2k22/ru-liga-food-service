package ru.liga.common.util.exceptions;

public class OrderStatusNotFoundException extends RuntimeException{

    public OrderStatusNotFoundException(String message){
        super(message);
    }
}
