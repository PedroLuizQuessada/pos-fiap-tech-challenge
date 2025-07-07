package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.datasources.UserTypeDataSource;
import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.dtos.responses.UserTypeResponse;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.mappers.UserTypeMapper;
import com.example.tech_challenge.usecases.CreateUserTypeUseCase;
import com.example.tech_challenge.usecases.DeleteUserTypeUseCase;
import com.example.tech_challenge.usecases.FindAllUserTypeUseCase;
import com.example.tech_challenge.usecases.UpdateUserTypeNameUseCase;

import java.util.List;

public class UserTypeController {

    private final UserDataSource userDataSource;
    private final UserTypeDataSource userTypeDataSource;

    public UserTypeController(UserDataSource userDataSource, UserTypeDataSource userTypeDataSource) {
        this.userDataSource = userDataSource;
        this.userTypeDataSource = userTypeDataSource;
    }

    public UserTypeResponse createUserType(UserTypeRequest userTypeRequest) {
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        CreateUserTypeUseCase createUserTypeUseCase = new CreateUserTypeUseCase(userTypeGateway);
        UserType userType = createUserTypeUseCase.execute(userTypeRequest);
        return UserTypeMapper.toAdminResponse(userType);
    }

    public UserTypeResponse updateUserTypeName(UserTypeRequest userTypeRequest, Long id) {
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        UpdateUserTypeNameUseCase updateUserTypeUserCase = new UpdateUserTypeNameUseCase(userTypeGateway);
        UserType userType = updateUserTypeUserCase.execute(userTypeRequest, id);
        return UserTypeMapper.toAdminResponse(userType);
    }

    public List<UserTypeResponse> findAllUserTypes() {
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        FindAllUserTypeUseCase findAllUserTypeUseCase = new FindAllUserTypeUseCase(userTypeGateway);
        List<UserType> userTypeList = findAllUserTypeUseCase.execute();
        return userTypeList.stream().map(UserTypeMapper::toAdminResponse).toList();
    }

    public void deleteUserType(Long id) {
        UserGateway userGateway = new UserGateway(userDataSource);
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        DeleteUserTypeUseCase deleteUserTypeUseCase = new DeleteUserTypeUseCase(userGateway, userTypeGateway);
        deleteUserTypeUseCase.execute(id);
    }
}
