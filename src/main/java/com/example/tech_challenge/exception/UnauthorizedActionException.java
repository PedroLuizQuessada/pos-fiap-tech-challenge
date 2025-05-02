package com.example.tech_challenge.exception;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String action) {
        super(String.format("Ação: '%s' não é permitida", action));
    }
}
