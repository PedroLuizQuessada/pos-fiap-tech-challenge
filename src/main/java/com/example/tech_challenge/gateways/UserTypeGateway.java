package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.UserTypeDataSource;
import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.exceptions.UserTypeNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserTypeGateway {

    private final UserTypeDataSource userTypeDataSource;

    public UserTypeGateway(UserTypeDataSource userTypeDataSource) {
        this.userTypeDataSource = userTypeDataSource;
    }

    public UserType createUserType(UserTypeDto createUserTypeDto) {
        UserTypeDto userTypeDto = userTypeDataSource.createUserType(createUserTypeDto);
        return createEntity(userTypeDto);
    }

    public UserType updateUserType(UserTypeDto updateUserTypeDto) {
        UserTypeDto userTypeDto = userTypeDataSource.updateUserType(updateUserTypeDto);
        return createEntity(userTypeDto);
    }

    public List<UserType> findAllUserTypes() {
        List<UserTypeDto> userTypeList = userTypeDataSource.findAllUserTypes();
        return userTypeList.stream().map(this::createEntity).toList();
    }

    public UserType findUserTypeById(Long id) {
        if (Objects.isNull(id))
            throw new UserTypeNotFoundException();

        Optional<UserTypeDto> optionalUserTypeDto = userTypeDataSource.findUserTypeById(id);

        if (optionalUserTypeDto.isEmpty())
            throw new UserTypeNotFoundException();

        return createEntity(optionalUserTypeDto.get());
    }

    public void delete(UserTypeDto userTypeDto) {

        userTypeDataSource.deleteUserType(userTypeDto);
    }

    private UserType createEntity(UserTypeDto userTypeDto) {
        return new UserType(userTypeDto.id(), userTypeDto.name());
    }
}
