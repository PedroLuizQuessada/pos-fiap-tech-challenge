package com.example.tech_challenge.exceptions;

public class MenuItemNameAlreadyInUseException extends RuntimeException {
    public MenuItemNameAlreadyInUseException() {
        super("Nome do item do cardápio já está sendo usado");
    }
}
