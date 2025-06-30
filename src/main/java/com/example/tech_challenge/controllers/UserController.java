package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.datasources.UserTypeDataSource;
import com.example.tech_challenge.dtos.requests.CreateUserRequest;
import com.example.tech_challenge.dtos.requests.UpdateUserPasswordRequest;
import com.example.tech_challenge.dtos.requests.UpdateUserRequest;
import com.example.tech_challenge.dtos.responses.TokenResponse;
import com.example.tech_challenge.dtos.responses.UserResponse;
import com.example.tech_challenge.entities.Token;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.presenters.TokenPresenter;
import com.example.tech_challenge.presenters.UserPresenter;
import com.example.tech_challenge.usecases.*;

public class UserController {

    private final UserDataSource userDataSource;
    private final AddressDataSource addressDataSource;
    private final TokenDataSource tokenDataSource;
    private final UserTypeDataSource userTypeDataSource;

    public UserController(UserDataSource userDataSource, AddressDataSource addressDataSource, TokenDataSource tokenDataSource, UserTypeDataSource userTypeDataSource) {
        this.userDataSource = userDataSource;
        this.addressDataSource = addressDataSource;
        this.tokenDataSource = tokenDataSource;
        this.userTypeDataSource = userTypeDataSource;
    }

    public TokenResponse generateToken(String userType, String login) {
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        GenerateTokenUseCase generateTokenUseCase = new GenerateTokenUseCase(tokenGateway);
        Token token = generateTokenUseCase.execute(userType, login);
        return TokenPresenter.toResponse(token);
    }

    public UserResponse createUser(CreateUserRequest createUserRequest, boolean allowAdmin) {
        UserGateway userGateway = new UserGateway(userDataSource);
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        CreateUserUseCase createUserUseCase = new CreateUserUseCase(userGateway, userTypeGateway);
        User user = createUserUseCase.execute(createUserRequest, allowAdmin);
        return allowAdmin ? UserPresenter.toAdminResponse(user) : UserPresenter.toResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest updateUserRequest, String login) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(userGateway, addressGateway);
        User user = updateUserUseCase.execute(updateUserRequest, login);
        return UserPresenter.toResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest updateUserRequest, Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(userGateway, addressGateway);
        User user = updateUserUseCase.execute(updateUserRequest, id);
        return UserPresenter.toAdminResponse(user);
    }

    public void deleteUser(String login) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(userGateway, addressGateway);
        deleteUserUseCase.execute(login);
    }

    public void deleteUser(Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(userGateway, addressGateway);
        deleteUserUseCase.execute(id);
    }

    public void updatePasswordUser(UpdateUserPasswordRequest updateUserPasswordRequest, String login) {
        UserGateway userGateway = new UserGateway(userDataSource);
        UpdateUserPasswordUseCase updateUserPasswordUseCase = new UpdateUserPasswordUseCase(userGateway);
        updateUserPasswordUseCase.execute(updateUserPasswordRequest, login);
    }
}
