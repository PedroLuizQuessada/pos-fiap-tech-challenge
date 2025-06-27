package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.request.CreateUserRequest;
import com.example.tech_challenge.dtos.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.dtos.request.UpdateUserRequest;
import com.example.tech_challenge.dtos.response.TokenResponse;
import com.example.tech_challenge.dtos.response.UserResponse;
import com.example.tech_challenge.entities.Token;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.presenters.TokenPresenter;
import com.example.tech_challenge.presenters.UserPresenter;
import com.example.tech_challenge.usecases.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

public class UserController {

    private final UserDataSource userDataSource;
    private final AddressDataSource addressDataSource;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public UserController(UserDataSource userDataSource, AddressDataSource addressDataSource, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.userDataSource = userDataSource;
        this.addressDataSource = addressDataSource;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public TokenResponse generateToken(UserDetails userDetails, String oldToken) {
        GenerateTokenUseCase generateTokenUseCase = new GenerateTokenUseCase(jwtEncoder, jwtDecoder);
        Token token = generateTokenUseCase.execute(userDetails, oldToken);
        return TokenPresenter.toResponse(token);
    }

    public UserResponse createUser(CreateUserRequest createUserRequest, boolean allowAdmin) {
        UserGateway userGateway = new UserGateway(userDataSource);
        CreateUserUseCase createUserUseCase = new CreateUserUseCase(userGateway);
        User user = createUserUseCase.execute(createUserRequest, allowAdmin);
        return UserPresenter.toResponse(user);
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
        return UserPresenter.toResponse(user);
    }

    public void deleteUser(String login) {
        UserGateway userGateway = new UserGateway(userDataSource);
        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(userGateway);
        deleteUserUseCase.execute(login);
    }

    public void deleteUser(Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(userGateway);
        deleteUserUseCase.execute(id);
    }

    public void updatePasswordUser(UpdateUserPasswordRequest updateUserPasswordRequest, String login) {
        UserGateway userGateway = new UserGateway(userDataSource);
        UpdateUserPasswordUseCase updateUserPasswordUseCase = new UpdateUserPasswordUseCase(userGateway);
        updateUserPasswordUseCase.execute(updateUserPasswordRequest, login);
    }
}
