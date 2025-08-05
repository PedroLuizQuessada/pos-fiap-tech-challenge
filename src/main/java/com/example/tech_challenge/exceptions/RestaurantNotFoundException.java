package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.NotFoundException;

public class RestaurantNotFoundException extends NotFoundException {
    public RestaurantNotFoundException() {
        super("Restaurante não encontrado");
    }
}
