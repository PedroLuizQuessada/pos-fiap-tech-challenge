package com.example.tech_challenge.helper;

import com.example.tech_challenge.entities.Token;

public class TokenHelper {

    public static final String VALID_TOKEN = "token";
    public static final String VALID_LOGIN = "Login";

    public static Token getValidToken() {
        return new Token(VALID_TOKEN, VALID_LOGIN);
    }
}
