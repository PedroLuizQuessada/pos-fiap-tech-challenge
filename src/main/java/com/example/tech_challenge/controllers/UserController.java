package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.request.CreateUserRequest;
import com.example.tech_challenge.dtos.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.dtos.request.UpdateUserRequest;
import com.example.tech_challenge.dtos.response.LoginTokenResponse;
import com.example.tech_challenge.dtos.response.LoginUserResponse;
import com.example.tech_challenge.dtos.response.UserResponse;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.presenters.LoginPresenter;
import com.example.tech_challenge.presenters.UserPresenter;
import com.example.tech_challenge.usecases.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;

public class UserController {

    private final UserDataSource userDataSource;
    private final AddressDataSource addressDataSource;
    private final JwtEncoder jwtEncoder;

    public UserController(UserDataSource userDataSource, AddressDataSource addressDataSource, JwtEncoder jwtEncoder) {
        this.userDataSource = userDataSource;
        this.addressDataSource = addressDataSource;
        this.jwtEncoder = jwtEncoder;
    }

    public LoginTokenResponse login(Authentication authentication) {
        LoginUseCase loginUseCase = new LoginUseCase(jwtEncoder);
        String token = loginUseCase.execute(authentication);
        return LoginPresenter.toResponse(token);
    }

    public LoginUserResponse login(String login) {
        UserGateway userGateway = new UserGateway(this.userDataSource);
        LoginUseCase loginUseCase = new LoginUseCase(userGateway);
        User user = loginUseCase.execute(login);
        return LoginPresenter.toResponse(user);
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
