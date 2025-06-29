package com.example.tech_challenge.exceptions;

public class UserTypeNotFoundException extends RuntimeException {
    public UserTypeNotFoundException() {
        super("Tipo de usuário não encontrado");
    }
}
