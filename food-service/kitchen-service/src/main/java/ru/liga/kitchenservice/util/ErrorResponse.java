package ru.liga.kitchenservice.util;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private Timestamp timestamp;

    public ErrorResponse(String message, Timestamp timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
