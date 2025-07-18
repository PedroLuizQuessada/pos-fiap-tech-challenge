package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.exceptions.UserTypeNameAlreadyInUseException;
import com.example.tech_challenge.gateways.UserTypeGateway;

import java.util.Objects;

public class UpdateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public UpdateUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserType execute(UserTypeRequest updateUserType, Long id) {
        UserType oldUserType = userTypeGateway.findUserTypeById(id);
        if (!Objects.equals(updateUserType.name(), oldUserType.getName()))
            checkNameAlreadyInUse(updateUserType.name());
        UserType userType = new UserType(id, updateUserType.name());
        return userTypeGateway.updateUserType(new UserTypeDto(userType.getId(), userType.getName()));
    }

    private void checkNameAlreadyInUse(String name) {
        if (userTypeGateway.countByName(name) > 0) {
            throw new UserTypeNameAlreadyInUseException();
        }
    }
}
