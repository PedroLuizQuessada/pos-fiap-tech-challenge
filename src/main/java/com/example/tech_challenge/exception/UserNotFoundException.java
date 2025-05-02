package com.example.tech_challenge.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Usuário não encontrado");
    }

    public UserNotFoundException(Long id) {
        super(String.format("Usuário %d não encontrado", id));
    }
}
