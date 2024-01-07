package com.example.airtime.payments.exceptions;

import org.springframework.http.HttpStatus;

public class AirtimeException extends RuntimeException {
    private final HttpStatus httpStatus;

    public AirtimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
