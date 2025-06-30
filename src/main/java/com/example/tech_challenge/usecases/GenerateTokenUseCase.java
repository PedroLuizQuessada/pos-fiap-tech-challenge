package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.Token;
import com.example.tech_challenge.gateways.TokenGateway;

public class GenerateTokenUseCase {

    private final TokenGateway tokenGateway;

    public GenerateTokenUseCase(TokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public Token execute(String userType, String login) {
        return  tokenGateway.generateToken(userType, login);
    }

}
