package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.dtos.requests.CreateUserRequest;
import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.enums.UserTypeEnum;
import com.example.tech_challenge.exceptions.AdminCreationNotAllowedException;
import com.example.tech_challenge.exceptions.EmailAlreadyInUseException;
import com.example.tech_challenge.exceptions.LoginAlreadyInUseException;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.gateways.UserTypeGateway;

import java.util.Objects;

public class CreateUserUseCase {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public CreateUserUseCase(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    public User execute(CreateUserRequest createUser, boolean allowAdmin) {
        if (!allowAdmin && UserTypeEnum.ADMIN.getId().equals(createUser.userType()))
            throw new AdminCreationNotAllowedException();

        Address address = null;
        AddressDto addressDto = null;
        if (!Objects.isNull(createUser.address())) {
            address = new Address(null, createUser.address().state(), createUser.address().city(), createUser.address().street(),
                    createUser.address().number(), createUser.address().zipCode(), createUser.address().aditionalInfo());

            addressDto = new AddressDto(null, createUser.address().state(), createUser.address().city(), createUser.address().street(),
                    createUser.address().number(), createUser.address().zipCode(), createUser.address().aditionalInfo());
        }

        UserType userType = userTypeGateway.findUserTypeById(createUser.userType());
        UserTypeDto userTypeDto = new UserTypeDto(userType.getId(), userType.getName());

        User user = new User(null, createUser.name(), createUser.email(), createUser.login(), createUser.password(),
                null, address, userType, true);

        checkEmailAlreadyInUse(createUser.email());
        checkLoginAlreadyInUse(createUser.login());

        return userGateway.createUser(new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(),
                user.getLastUpdateDate(), addressDto, userTypeDto));
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
