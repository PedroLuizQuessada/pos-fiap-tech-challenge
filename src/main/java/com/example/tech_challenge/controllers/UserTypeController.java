package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.UserTypeDataSource;
import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.dtos.responses.UserTypeResponse;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.presenters.UserTypePresenter;
import com.example.tech_challenge.usecases.CreateUserTypeUseCase;
import com.example.tech_challenge.usecases.DeleteUserTypeUseCase;
import com.example.tech_challenge.usecases.FindAllUserTypeUseCase;
import com.example.tech_challenge.usecases.UpdateUserTypeUseCase;

import java.util.List;

public class UserTypeController {

    private final UserTypeDataSource userTypeDataSource;

    public UserTypeController(UserTypeDataSource userTypeDataSource) {
        this.userTypeDataSource = userTypeDataSource;
    }

    public UserTypeResponse createUserType(UserTypeRequest userTypeRequest) {
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        CreateUserTypeUseCase createUserTypeUseCase = new CreateUserTypeUseCase(userTypeGateway);
        UserType userType = createUserTypeUseCase.execute(userTypeRequest);
        return UserTypePresenter.toAdminResponse(userType);
    }

    public UserTypeResponse updateUserType(UserTypeRequest userTypeRequest, Long id) {
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        UpdateUserTypeUseCase updateUserTypeUserCase = new UpdateUserTypeUseCase(userTypeGateway);
        UserType userType = updateUserTypeUserCase.execute(userTypeRequest, id);
        return UserTypePresenter.toAdminResponse(userType);
    }

    public List<UserTypeResponse> findAllUserTypes() {
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        FindAllUserTypeUseCase findAllUserTypeUseCase = new FindAllUserTypeUseCase(userTypeGateway);
        List<UserType> userTypeList = findAllUserTypeUseCase.execute();
        return userTypeList.stream().map(UserTypePresenter::toAdminResponse).toList();
    }

    public void deleteUserType(Long id) {
        UserTypeGateway userTypeGateway = new UserTypeGateway(userTypeDataSource);
        DeleteUserTypeUseCase deleteUserTypeUseCase = new DeleteUserTypeUseCase(userTypeGateway);
        deleteUserTypeUseCase.execute(id);
    }
}
