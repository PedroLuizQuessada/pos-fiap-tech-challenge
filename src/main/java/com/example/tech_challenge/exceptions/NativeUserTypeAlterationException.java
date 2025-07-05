package com.example.tech_challenge.exceptions;

public class NativeUserTypeAlterationException extends RuntimeException {
    public NativeUserTypeAlterationException() {
        super("Não é permitida a alteração de tipos de usuário nativos do sistema");
    }
}
