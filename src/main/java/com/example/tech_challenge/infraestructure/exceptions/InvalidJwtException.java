package com.example.tech_challenge.infraestructure.exceptions;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException() {
        super("Token de acesso inválido");
    }
}
