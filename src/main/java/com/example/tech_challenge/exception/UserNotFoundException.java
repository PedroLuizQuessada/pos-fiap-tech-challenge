package com.example.tech_challenge.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) {
        super(String.format("Usuário '%s' não encontrado", login));
    }
}
