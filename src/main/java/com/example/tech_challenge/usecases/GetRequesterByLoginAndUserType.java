package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.gateways.RequesterGateway;

public class GetRequesterByLoginAndUserType {

    private final RequesterGateway requesterGateway;

    public GetRequesterByLoginAndUserType(RequesterGateway requesterGateway) {
        this.requesterGateway = requesterGateway;
    }

    public Requester execute(String userType, String login) {
        return requesterGateway.getRequester(userType, login);
    }
}
