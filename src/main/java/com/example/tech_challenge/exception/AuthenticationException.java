package com.example.tech_challenge.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Usuário ou senha incorretos");
    }
}
