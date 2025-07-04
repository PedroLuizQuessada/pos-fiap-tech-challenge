package com.example.tech_challenge.exceptions;

public class RestaurantNameAlreadyInUseException extends RuntimeException {
    public RestaurantNameAlreadyInUseException() {
        super("Nome do restaurante já está sendo usado");
    }
}
