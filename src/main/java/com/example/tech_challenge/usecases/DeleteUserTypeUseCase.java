package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.exceptions.UserTypeDeletionException;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.gateways.UserTypeGateway;

public class DeleteUserTypeUseCase {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public DeleteUserTypeUseCase(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    public void execute(Long id) {
        UserType userType = userTypeGateway.findUserTypeById(id);
        checkUserByUserType(id);
        userTypeGateway.delete(new UserTypeDto(userType.getId(), userType.getName()));
    }

    private void checkUserByUserType(Long id) {
        Long numUsersByUserType = userGateway.countByUserType(id);
        if (numUsersByUserType > 0) {
            throw new UserTypeDeletionException(numUsersByUserType);
        }
    }
}
