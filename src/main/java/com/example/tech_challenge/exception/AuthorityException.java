package com.example.tech_challenge.exception;

public class AuthorityException extends RuntimeException {
    public AuthorityException() {
        super("Recurso não permitido ao usuário");
    }
}
