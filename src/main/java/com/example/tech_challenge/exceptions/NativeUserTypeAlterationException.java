package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.BadRequestException;

public class NativeUserTypeAlterationException extends BadRequestException {
    public NativeUserTypeAlterationException() {
        super("Não é permitida a alteração de tipos de usuário nativos do sistema");
    }
}
