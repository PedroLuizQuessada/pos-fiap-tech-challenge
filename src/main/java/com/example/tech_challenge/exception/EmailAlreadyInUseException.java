package com.example.tech_challenge.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super("E-mail já está sendo usado");
    }
}
