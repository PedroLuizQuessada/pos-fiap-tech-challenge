package com.example.tech_challenge.usecases.updateuser;

import com.example.tech_challenge.dtos.requests.UpdateUserRequest;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.gateways.UserGateway;

public class UpdateUserByRequesterUseCase {

    private final UserGateway userGateway;
    private final TokenGateway tokenGateway;
    private final UpdateUserUseCase updateUserUseCase;

    public UpdateUserByRequesterUseCase(UserGateway userGateway, AddressGateway addressGateway, TokenGateway tokenGateway) {
        this.userGateway = userGateway;
        this.tokenGateway = tokenGateway;
        this.updateUserUseCase = new UpdateUserUseCase(userGateway, addressGateway);
    }

    public User execute(UpdateUserRequest updateUserRequest, String token) {
        Requester requester = tokenGateway.getRequester(token);
        User oldUser = userGateway.findUserByLogin(requester.getLogin());
        return updateUserUseCase.updateUser(updateUserRequest, oldUser);
    }

}
