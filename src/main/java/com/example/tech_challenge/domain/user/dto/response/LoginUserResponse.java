package com.example.tech_challenge.domain.user.dto.response;

public record LoginUserResponse(String message) {

    public LoginUserResponse(String message) {
        this.message = String.format("Bem-vindo(a) ao sistema %s", message);
    }
}
