package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.enums.UserTypeEnum;
import com.example.tech_challenge.exceptions.NativeUserTypeAlterationException;
import com.example.tech_challenge.exceptions.UserTypeWithUsersDeletionException;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.mappers.UserTypeMapper;

import java.util.Objects;

public class DeleteUserTypeUseCase {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public DeleteUserTypeUseCase(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    public void execute(Long id) {
        UserType userType = userTypeGateway.findUserTypeById(id);
        if (!Objects.isNull(UserTypeEnum.getUserTypeById(id)))
            throw new NativeUserTypeAlterationException();
        checkUserByUserType(id);
        userTypeGateway.delete(UserTypeMapper.toDto(userType));
    }

    private void checkUserByUserType(Long id) {
        Long numUsersByUserType = userGateway.countByUserType(id);
        if (numUsersByUserType > 0) {
            throw new UserTypeWithUsersDeletionException(numUsersByUserType);
        }
    }
}
