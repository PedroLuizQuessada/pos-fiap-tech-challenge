package com.example.tech_challenge.usecases.deleteuser;

import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.*;
import com.example.tech_challenge.mappers.UserMapper;

public class DeleteUserByRequesterUseCase {

    private final UserGateway userGateway;
    private final TokenGateway tokenGateway;
    private final DeleteUserUseCase deleteUserUseCase;

    public DeleteUserByRequesterUseCase(UserGateway userGateway, AddressGateway addressGateway, RestaurantGateway restaurantGateway,
                             MenuItemGateway menuItemGateway, TokenGateway tokenGateway) {
        this.userGateway = userGateway;
        this.tokenGateway = tokenGateway;
        this.deleteUserUseCase = new DeleteUserUseCase(userGateway, addressGateway, restaurantGateway, menuItemGateway);
    }

    public void execute(String token) {
        Requester requester = tokenGateway.getRequester(token);
        User user = userGateway.findUserByLogin(requester.getLogin());
        deleteUserUseCase.deleteUser(UserMapper.toDto(user));
    }

}
