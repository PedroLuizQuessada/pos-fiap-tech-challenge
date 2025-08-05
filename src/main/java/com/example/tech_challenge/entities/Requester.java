package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import com.example.tech_challenge.exceptions.RequesterException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Requester {
    private final String userType;
    private final String login;

    public Requester(String userType, String login) {

        String message = "";

        try {
            validateUserType(userType);
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

        this.userType = userType;
        this.login = login;
    }

    private void validateUserType(String userType) {
        if (Objects.isNull(userType) || userType.isEmpty())
            throw new RequesterException("Falha ao detectar o tipo de usuário do requisitor, favor contactar o administrador.");
    }

    private void validateLogin(String login) {
        if (Objects.isNull(login) || login.isEmpty())
            throw new RequesterException("Falha ao detectar o login do requisitor, favor contactar o administrador.");
    }

}
