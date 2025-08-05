package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.NotFoundException;

public class UserTypeNotFoundException extends NotFoundException {
    public UserTypeNotFoundException() {
        super("Tipo de usuário não encontrado");
    }
}
