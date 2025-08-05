package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.BadRequestException;

public class EmailAlreadyInUseException extends BadRequestException {
    public EmailAlreadyInUseException() {
        super("E-mail já está sendo usado");
    }
}
