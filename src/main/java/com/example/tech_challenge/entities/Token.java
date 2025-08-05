package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import com.example.tech_challenge.exceptions.TokenException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Token {
    private final String token;
    private final String login;

    public Token(String token, String login) {

        String message = "";

        try {
            validateToken(token);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateLogin(login);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.token = token;
        this.login = login;
    }

    private void validateToken(String token) {
        if (Objects.isNull(token) || token.isEmpty())
            throw new TokenException("O token deve possuir um valor.");
    }

    private void validateLogin(String login) {
        if (Objects.isNull(login) || login.isEmpty())
            throw new TokenException("O login do token deve possuir um valor.");
    }
}
