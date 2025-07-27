package com.example.tech_challenge.usecases.createuser;

import com.example.tech_challenge.dtos.requests.AdminCreateUserRequest;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.mappers.UserMapper;

public class AdminCreateUserUseCase {

    private final UserTypeGateway userTypeGateway;
    private final CreateUserUseCase createUserUseCase;

    public AdminCreateUserUseCase(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
        this.createUserUseCase = new CreateUserUseCase(userGateway, userTypeGateway);
    }

    public User execute(AdminCreateUserRequest createUser) {
        UserType userType = userTypeGateway.findUserTypeById(createUser.userType());
        User user = UserMapper.toEntity(createUser, userType, true);
        return createUserUseCase.createUser(createUser, user);
    }

}
