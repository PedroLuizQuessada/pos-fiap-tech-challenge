package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.UpdateUserPasswordRequest;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.mappers.UserMapper;

public class UpdateUserPasswordUseCase {

    private final UserGateway userGateway;
    private final TokenGateway tokenGateway;

    public UpdateUserPasswordUseCase(UserGateway userGateway, TokenGateway tokenGateway) {
        this.userGateway = userGateway;
        this.tokenGateway = tokenGateway;
    }

    public void execute(UpdateUserPasswordRequest updateUserPasswordRequest, String token) {
        Requester requester = tokenGateway.getRequester(token);
        User user = userGateway.findUserByLogin(requester.getLogin());
        user.setPassword(updateUserPasswordRequest.newPassword());
        userGateway.updateUser(UserMapper.toDto(user));
    }
}
