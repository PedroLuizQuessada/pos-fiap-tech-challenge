package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
}
