package com.example.tech_challenge.helper;

import com.example.tech_challenge.entities.Requester;

public class RequesterHelper {

    public static final String VALID_USER_TYPE = "Tipo de Usuário";
    public static final String VALID_LOGIN = "Login";

    public static Requester getValidRequester() {
        return new Requester(VALID_USER_TYPE, VALID_LOGIN);
    }

}
