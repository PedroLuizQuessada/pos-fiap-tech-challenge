package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.gateways.UserTypeGateway;

public class CreateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public CreateUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserType execute(UserTypeRequest createUserType) {
        UserType userType = new UserType(null, createUserType.name());
        return userTypeGateway.createUserType(new UserTypeDto(userType.getId(), userType.getName()));
    }
}
