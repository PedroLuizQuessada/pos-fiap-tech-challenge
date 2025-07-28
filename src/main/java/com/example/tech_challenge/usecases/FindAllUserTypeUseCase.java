package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.gateways.UserTypeGateway;

import java.util.List;

public class FindAllUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public FindAllUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public List<UserType> execute(int page, int size) {
        return userTypeGateway.findAllUserTypes(page, size);
    }
}
