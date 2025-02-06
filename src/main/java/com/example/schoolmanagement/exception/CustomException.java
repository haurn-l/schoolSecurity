package com.example.schoolmanagement.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private String message;

    public CustomException(String message, HttpStatus notFound) {
        super(message);
        this.message = message;
    }

    public CustomException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
