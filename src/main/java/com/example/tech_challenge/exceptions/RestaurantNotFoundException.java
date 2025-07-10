package com.example.tech_challenge.exceptions;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException() {
        super("Restaurante não encontrado");
    }
}
