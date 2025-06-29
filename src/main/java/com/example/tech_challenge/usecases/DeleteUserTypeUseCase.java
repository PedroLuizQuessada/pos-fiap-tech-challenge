package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.gateways.UserTypeGateway;

public class DeleteUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public DeleteUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public void execute(Long id) {
        UserType userType = userTypeGateway.findUserTypeById(id);
        userTypeGateway.delete(new UserTypeDto(userType.getId(), userType.getName()));
    }
}
