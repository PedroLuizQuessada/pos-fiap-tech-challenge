package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.responses.TokenResponse;
import com.example.tech_challenge.entities.Token;

public class TokenMapper {

    private TokenMapper(){}

    public static TokenResponse toResponse(Token token) {
        return new TokenResponse(token.getToken(), token.getLogin());
    }
}
