package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class UserType {
    private final Long id;
    private String name;

    public UserType(Long id, String name) {

        String message = "";

        try {
            validateName(name);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty())
            throw new BadArgumentException("O tipo de usuário deve possuir um nome.");

        if (name.length() > 45)
            throw new BadArgumentException("O nome do tipo de usuário deve possuir até 45 caracteres.");
    }
}
