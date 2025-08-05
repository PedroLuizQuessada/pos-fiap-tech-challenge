package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.BadRequestException;

public class MenuItemNameAlreadyInUseException extends BadRequestException {
    public MenuItemNameAlreadyInUseException() {
        super("Nome do item do cardápio já está sendo usado");
    }
}
