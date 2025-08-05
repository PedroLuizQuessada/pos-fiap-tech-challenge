package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.NotFoundException;

public class MenuItemNotFoundException extends NotFoundException {
    public MenuItemNotFoundException() {
        super("Item do cardápio não encontrado");
    }
}
