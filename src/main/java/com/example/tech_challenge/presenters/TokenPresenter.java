package com.example.tech_challenge.presenters;

import com.example.tech_challenge.dtos.responses.TokenResponse;
import com.example.tech_challenge.entities.Token;

public class TokenPresenter {

    private TokenPresenter(){}

    public static TokenResponse toResponse(Token token) {
        return new TokenResponse(token.getToken(), token.getLogin());
    }
}
