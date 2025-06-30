package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.RequesterDataSource;
import com.example.tech_challenge.dtos.RequesterDto;
import com.example.tech_challenge.entities.Requester;

public class RequesterGateway {

    private final RequesterDataSource requesterDataSource;

    public RequesterGateway(RequesterDataSource requesterDataSource) {
        this.requesterDataSource = requesterDataSource;
    }

    public Requester getRequester(String userType, String login) {
        RequesterDto requesterDto = requesterDataSource.getRequester(userType, login);
        return createEntity(requesterDto);
    }

    private Requester createEntity(RequesterDto requesterDto) {
        return new Requester(requesterDto.userType(), requesterDto.login());
    }
}
