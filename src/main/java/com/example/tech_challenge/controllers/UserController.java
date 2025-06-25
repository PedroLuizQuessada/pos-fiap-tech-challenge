package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.request.CreateUserRequest;
import com.example.tech_challenge.dtos.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.dtos.request.UpdateUserRequest;
import com.example.tech_challenge.dtos.response.LoginUserResponse;
import com.example.tech_challenge.dtos.response.UserResponse;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.presenters.LoginUserPresenter;
import com.example.tech_challenge.presenters.UserPresenter;
import com.example.tech_challenge.usecases.*;

public class UserController {

    private final UserDataSource userDataSource;
    private final AddressDataSource addressDataSource;

    public UserController(UserDataSource userDataSource, AddressDataSource addressDataSource) {
        this.userDataSource = userDataSource;
        this.addressDataSource = addressDataSource;
    }

    public LoginUserResponse login(String authToken) {
        UserGateway userGateway = new UserGateway(userDataSource);
        LoginUseCase loginUseCase = new LoginUseCase(userGateway);
        User user = loginUseCase.execute(authToken, false);
        return LoginUserPresenter.toResponse(user);
    }

    public UserResponse createUser(CreateUserRequest createUserRequest, boolean allowAdmin) {
        UserGateway userGateway = new UserGateway(userDataSource);
        CreateUserUseCase createUserUseCase = new CreateUserUseCase(userGateway);
        User user = createUserUseCase.execute(createUserRequest, allowAdmin);
        return UserPresenter.toResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest updateUserRequest, Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(userGateway, addressGateway);
        User user = updateUserUseCase.execute(updateUserRequest, id);
        return UserPresenter.toResponse(user);
    }

    public void deleteUser(Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(userGateway);
        deleteUserUseCase.execute(id);
    }

    public void updatePasswordUser(UpdateUserPasswordRequest updateUserPasswordRequest, Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        UpdateUserPasswordUseCase updateUserPasswordUseCase = new UpdateUserPasswordUseCase(userGateway);
        updateUserPasswordUseCase.execute(updateUserPasswordRequest, id);
    }
}
