package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.BadRequestException;

public class AdminCreationNotAllowedException extends BadRequestException {
    public AdminCreationNotAllowedException() {
        super("Não é permitido um usuário não autenticado criar um usuário admin");
    }
}
