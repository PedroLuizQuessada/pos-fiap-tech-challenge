package com.example.tech_challenge.exceptions;

public class UserTypeNameAlreadyInUseException extends RuntimeException {
    public UserTypeNameAlreadyInUseException() {
        super("Nome do tipo de usuário já está sendo usado");
    }
}
