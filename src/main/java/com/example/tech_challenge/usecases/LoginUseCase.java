package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.UserGateway;

public class LoginUseCase {

    private final UserGateway userGateway;

    public LoginUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(String login) {
        return userGateway.findUserByLogin(login);
    }
}
