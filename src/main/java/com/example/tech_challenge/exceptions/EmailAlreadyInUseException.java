package com.example.tech_challenge.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super("E-mail já está sendo usado");
    }
}
