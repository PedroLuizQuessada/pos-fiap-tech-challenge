package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.enums.UserTypeEnum;
import com.example.tech_challenge.exceptions.NativeUserTypeAlterationException;
import com.example.tech_challenge.exceptions.UserTypeNameAlreadyInUseException;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.mappers.UserTypeMapper;

import java.util.Objects;

public class UpdateUserTypeNameUseCase {

    private final UserTypeGateway userTypeGateway;

    public UpdateUserTypeNameUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserType execute(UserTypeRequest updateUserType, Long id) {
        if (!Objects.isNull(UserTypeEnum.getUserTypeById(id)))
            throw new NativeUserTypeAlterationException();
        UserType userType = userTypeGateway.findUserTypeById(id);

        String oldName = userType.getName();

        userType.setName(updateUserType.name());

        if (!Objects.equals(updateUserType.name(), oldName))
            checkNameAlreadyInUse(updateUserType.name());

        return userTypeGateway.updateUserType(UserTypeMapper.toDto(userType));
    }

    private void checkNameAlreadyInUse(String name) {
        if (userTypeGateway.countByName(name) > 0) {
            throw new UserTypeNameAlreadyInUseException();
        }
    }
}
