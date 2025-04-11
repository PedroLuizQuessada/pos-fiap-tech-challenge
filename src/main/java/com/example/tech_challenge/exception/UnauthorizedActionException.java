package com.example.tech_challenge.exception;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String action, String login) {
        super(String.format("Ação: '%s' não é permitida ao usuário: '%s'", action, login));
    }
}
