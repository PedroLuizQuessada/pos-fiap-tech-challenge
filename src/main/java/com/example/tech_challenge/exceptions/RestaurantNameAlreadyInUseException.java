package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.BadRequestException;

public class RestaurantNameAlreadyInUseException extends BadRequestException {
    public RestaurantNameAlreadyInUseException() {
        super("Nome do restaurante já está sendo usado");
    }
}
