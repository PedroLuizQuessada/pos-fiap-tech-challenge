package com.example.tech_challenge.infraestructure.exceptions;

public class BadJpaArgumentException extends RuntimeException {
    public BadJpaArgumentException(String message) {
        super(message);
    }
}
