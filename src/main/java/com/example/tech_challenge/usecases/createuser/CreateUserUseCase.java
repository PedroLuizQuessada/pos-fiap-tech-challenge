package com.example.tech_challenge.usecases.createuser;

import com.example.tech_challenge.dtos.requests.CreateUser;
import com.example.tech_challenge.dtos.requests.CreateUserRequest;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.enums.UserTypeEnum;
import com.example.tech_challenge.exceptions.AdminCreationNotAllowedException;
import com.example.tech_challenge.exceptions.EmailAlreadyInUseException;
import com.example.tech_challenge.exceptions.LoginAlreadyInUseException;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.mappers.UserMapper;

public class CreateUserUseCase {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public CreateUserUseCase(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    public User execute(CreateUserRequest createUser) {
        if (UserTypeEnum.ADMIN.name().equals(createUser.userType()))
            throw new AdminCreationNotAllowedException();

        UserType userType = userTypeGateway.findUserTypeByName(createUser.userType());
        User user = UserMapper.toEntity(createUser, userType, true);
        return createUser(createUser, user);
    }

    User createUser(CreateUser createUser, User user) {
        checkEmailAlreadyInUse(createUser.email());
        checkLoginAlreadyInUse(createUser.login());

        return userGateway.createUser(UserMapper.toDto(user));
    }

    private void checkEmailAlreadyInUse(String email) {
        if (userGateway.countByEmail(email) > 0) {
            throw new EmailAlreadyInUseException();
        }
    }

    private void checkLoginAlreadyInUse(String login) {
        if (userGateway.countByLogin(login) > 0) {
            throw new LoginAlreadyInUseException();
        }
    }
}
