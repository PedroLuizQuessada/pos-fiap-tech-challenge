package com.example.tech_challenge.domain.user.dto.response;

import lombok.Getter;

@Getter
public class LoginUserResponse {

    private final String message;

    public LoginUserResponse(String name) {
        this.message = String.format("Bem-vindo(a) ao sistema %s", name);
    }
}
