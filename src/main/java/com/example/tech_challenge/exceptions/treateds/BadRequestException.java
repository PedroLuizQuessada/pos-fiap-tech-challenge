package com.example.tech_challenge.exceptions.treateds;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
