package com.example.tech_challenge.entities;

import com.example.tech_challenge.exception.TokenException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Token {
    private final String token;
    private final String login;

    public Token(String token, String login) {

        validateToken(token);
        validateLogin(login);

        this.token = token;
        this.login = login;
    }

    private void validateToken(String token) {
        if (Objects.isNull(token) || token.isEmpty())
            throw new TokenException("O token deve possuir um valor");
    }

    private void validateLogin(String login) {
        if (Objects.isNull(login) || login.isEmpty())
            throw new TokenException("O login do token deve possuir um valor");
    }
}
