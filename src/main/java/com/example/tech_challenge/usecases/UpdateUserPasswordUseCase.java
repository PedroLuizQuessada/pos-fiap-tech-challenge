package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.UpdateUserPasswordRequest;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.mappers.UserMapper;

public class UpdateUserPasswordUseCase {

    private final UserGateway userGateway;

    public UpdateUserPasswordUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(UpdateUserPasswordRequest updateUserPasswordRequest, String login) {
        User user = userGateway.findUserByLogin(login);
        user.setPassword(updateUserPasswordRequest.newPassword());
        userGateway.updateUser(UserMapper.toDto(user));
    }
}
