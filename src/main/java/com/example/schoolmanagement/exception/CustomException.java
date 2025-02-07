package com.example.schoolmanagement.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CustomException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
