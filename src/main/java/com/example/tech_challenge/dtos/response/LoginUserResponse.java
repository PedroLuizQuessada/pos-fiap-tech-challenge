package com.example.tech_challenge.dtos.response;

public record LoginUserResponse(String message) {

    public LoginUserResponse(String message) {
        this.message = String.format("Bem-vindo(a) ao sistema %s", message);
    }
}
