package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.*;
import com.example.tech_challenge.dtos.requests.CreateUserRequest;
import com.example.tech_challenge.dtos.requests.UpdateUserPasswordRequest;
import com.example.tech_challenge.dtos.requests.UpdateUserRequest;
import com.example.tech_challenge.dtos.responses.TokenResponse;
import com.example.tech_challenge.dtos.responses.UserResponse;
import com.example.tech_challenge.entities.Token;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.*;
import com.example.tech_challenge.mappers.TokenMapper;
import com.example.tech_challenge.mappers.UserMapper;
import com.example.tech_challenge.usecases.*;

public class UserController {

    private final UserDataSource userDataSource;
    private final AddressDataSource addressDataSource;
    private final TokenDataSource tokenDataSource;
    private final UserTypeDataSource userTypeDataSource;
    private final RestaurantDataSource restaurantDataSource;

    public UserController(UserDataSource userDataSource, AddressDataSource addressDataSource, TokenDataSource tokenDataSource,
                          UserTypeDataSource userTypeDataSource, RestaurantDataSource restaurantDataSource) {
        this.userDataSource = userDataSource;
        this.addressDataSource = addressDataSource;
        this.tokenDataSource = tokenDataSource;
        this.userTypeDataSource = userTypeDataSource;
        this.restaurantDataSource = restaurantDataSource;
    }

    public TokenResponse generateToken(String userType, String login) {
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        GenerateTokenUseCase generateTokenUseCase = new GenerateTokenUseCase(tokenGateway);
        Token token = generateTokenUseCase.execute(userType, login);
        return TokenMapper.toResponse(token);
    }

    public UserResponse createUser(CreateUserRequest createUserRequest, boolean allowAdmin) {
        UserGateway userGateway = new UserGateway(userDataSource);
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        CreateUserUseCase createUserUseCase = new CreateUserUseCase(userGateway, userTypeGateway);
        User user = createUserUseCase.execute(createUserRequest, allowAdmin);
        return allowAdmin ? UserMapper.toAdminResponse(user) : UserMapper.toResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest updateUserRequest, String login) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(userGateway, addressGateway);
        User user = updateUserUseCase.execute(updateUserRequest, login);
        return UserMapper.toResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest updateUserRequest, Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(userGateway, addressGateway);
        User user = updateUserUseCase.execute(updateUserRequest, id);
        return UserMapper.toAdminResponse(user);
    }

    public void deleteUser(String login) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(userGateway, addressGateway, restaurantGateway);
        deleteUserUseCase.execute(login);
    }

    public void deleteUser(Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        DeleteUserUseCase deleteUserUseCase = new DeleteUserUseCase(userGateway, addressGateway, restaurantGateway);
        deleteUserUseCase.execute(id);
    }

    public void updatePasswordUser(UpdateUserPasswordRequest updateUserPasswordRequest, String login) {
        UserGateway userGateway = new UserGateway(userDataSource);
        UpdateUserPasswordUseCase updateUserPasswordUseCase = new UpdateUserPasswordUseCase(userGateway);
        updateUserPasswordUseCase.execute(updateUserPasswordRequest, login);
    }
}
