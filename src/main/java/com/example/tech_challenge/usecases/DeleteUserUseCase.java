package com.example.tech_challenge.usecases;

import com.example.tech_challenge.gateways.UserGateway;

public class DeleteUserUseCase {

    private final UserGateway userGateway;

    public DeleteUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(String login) {
        userGateway.deleteUserByLogin(login);
    }

    public void execute(Long id) {
        userGateway.deleteUserById(id);
    }
}
