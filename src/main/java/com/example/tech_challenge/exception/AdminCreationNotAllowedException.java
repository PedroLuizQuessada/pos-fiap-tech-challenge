package com.example.tech_challenge.exception;

public class AdminCreationNotAllowedException extends RuntimeException {
    public AdminCreationNotAllowedException() {
        super("Não é permitido um usuário não autenticado criar um usuário admin");
    }
}
