package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.exceptions.UserTypeNameAlreadyInUseException;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.mappers.UserTypeMapper;

public class CreateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public CreateUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserType execute(UserTypeRequest createUserType) {
        UserType userType = UserTypeMapper.toEntity(createUserType);
        checkNameAlreadyInUse(createUserType.name());
        return userTypeGateway.createUserType(UserTypeMapper.toDto(userType));
    }

    private void checkNameAlreadyInUse(String name) {
        if (userTypeGateway.countByName(name) > 0) {
            throw new UserTypeNameAlreadyInUseException();
        }
    }
}
