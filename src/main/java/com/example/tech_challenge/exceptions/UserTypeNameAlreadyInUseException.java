package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.BadRequestException;

public class UserTypeNameAlreadyInUseException extends BadRequestException {
    public UserTypeNameAlreadyInUseException() {
        super("Nome do tipo de usuário já está sendo usado");
    }
}
