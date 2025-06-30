package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.gateways.TokenGateway;

public class GetRequesterByTokenUseCase {

    private final TokenGateway tokenGateway;

    public GetRequesterByTokenUseCase(TokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public Requester execute(String token) {
        return tokenGateway.getRequester(token);
    }
}
