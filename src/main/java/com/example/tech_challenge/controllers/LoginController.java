package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.responses.LoginResponse;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.presenters.LoginPresenter;
import com.example.tech_challenge.usecases.LoginUseCase;

public class LoginController {

    private final UserDataSource userDataSource;

    public LoginController(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public LoginResponse login(String login) {
        UserGateway userGateway = new UserGateway(this.userDataSource);
        LoginUseCase loginUseCase = new LoginUseCase(userGateway);
        User user = loginUseCase.execute(login);
        return LoginPresenter.toResponse(user);
    }
}
