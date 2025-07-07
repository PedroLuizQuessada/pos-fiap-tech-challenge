package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.RequesterDataSource;
import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.dtos.responses.RequesterResponse;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.gateways.RequesterGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.mappers.RequesterMapper;
import com.example.tech_challenge.usecases.GetRequesterByLoginAndUserType;
import com.example.tech_challenge.usecases.GetRequesterByTokenUseCase;

public class RequesterController {

    private final RequesterDataSource requesterDataSource;
    private final TokenDataSource tokenDataSource;

    public RequesterController(RequesterDataSource requesterDataSource, TokenDataSource tokenDataSource) {
        this.requesterDataSource = requesterDataSource;
        this.tokenDataSource = tokenDataSource;
    }

    public RequesterResponse getRequester(String userType, String login) {
        RequesterGateway requesterGateway = new RequesterGateway(requesterDataSource);
        GetRequesterByLoginAndUserType getRequesterUseCase = new GetRequesterByLoginAndUserType(requesterGateway);
        Requester requester = getRequesterUseCase.execute(userType, login);
        return RequesterMapper.toResponse(requester);
    }

    public RequesterResponse getRequester(String token) {
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        GetRequesterByTokenUseCase getRequesterUseCase = new GetRequesterByTokenUseCase(tokenGateway);
        Requester requester = getRequesterUseCase.execute(token);
        return RequesterMapper.toResponse(requester);
    }
}
