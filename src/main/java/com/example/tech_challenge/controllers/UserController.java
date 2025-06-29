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
import org.springframework.security.core.userdetails.UserDetails;

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

    public TokenResponse generateToken(UserDetails userDetails, String oldToken) {
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        GenerateTokenUseCase generateTokenUseCase = new GenerateTokenUseCase(tokenGateway);
        Token token = generateTokenUseCase.execute(userDetails, oldToken);
        return TokenPresenter.toResponse(token);
    }

    public UserResponse createUser(CreateUserRequest createUserRequest, boolean allowAdmin) {
        UserGateway userGateway = new UserGateway(userDataSource);
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        CreateUserUseCase createUserUseCase = new CreateUserUseCase(userGateway, userTypeGateway);
        User user = createUserUseCase.execute(createUserRequest, allowAdmin);
        return allowAdmin ? UserPresenter.toAdminResponse(user) : UserPresenter.toResponse(user);
    }

    public UserResponse updateUser(UserDetails userDetails, String token, UpdateUserRequest updateUserRequest) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(userGateway, addressGateway, tokenGateway);
        User user = updateUserUseCase.execute(updateUserRequest, userDetails, token);
        return UserPresenter.toResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest updateUserRequest, Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(userGateway, addressGateway, tokenGateway);
        User user = updateUserUseCase.execute(updateUserRequest, id);
        return UserPresenter.toAdminResponse(user);
    }

    public String deleteUser(UserDetails userDetails, String token) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(userGateway, addressGateway, tokenGateway);
        return deleteUserUseCase.execute(userDetails, token);
    }

    public void deleteUser(Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(userGateway, addressGateway, tokenGateway);
        deleteUserUseCase.execute(id);
    }

    public String updatePasswordUser(UserDetails userDetails, String token, UpdateUserPasswordRequest updateUserPasswordRequest) {
        UserGateway userGateway = new UserGateway(userDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        UpdateUserPasswordUseCase updateUserPasswordUseCase = new UpdateUserPasswordUseCase(userGateway, tokenGateway);
        return updateUserPasswordUseCase.execute(updateUserPasswordRequest, userDetails, token);
    }
}
