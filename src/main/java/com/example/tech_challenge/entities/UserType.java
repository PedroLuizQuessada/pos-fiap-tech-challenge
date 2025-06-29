package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class UserType {
    private final Long id;
    private final String name;

    public UserType(Long id, String name) {
        validateName(name);

        this.id = id;
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty())
            throw new BadArgumentException("O tipo de usuário deve possuir um nome");

        if (name.length() > 45)
            throw new BadArgumentException("O nome do tipo de usuário deve possuir até 45 caracteres");
    }
}
