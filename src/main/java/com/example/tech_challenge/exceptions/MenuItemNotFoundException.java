package com.example.tech_challenge.exceptions;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException() {
        super("Item do cardápio não encontrado");
    }
}
