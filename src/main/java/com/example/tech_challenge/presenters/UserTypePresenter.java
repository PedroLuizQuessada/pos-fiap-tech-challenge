package com.example.tech_challenge.presenters;

import com.example.tech_challenge.dtos.responses.UserTypeResponse;
import com.example.tech_challenge.entities.UserType;

public class UserTypePresenter {

    private UserTypePresenter(){}

    public static UserTypeResponse toResponse(UserType userType) {
        return new UserTypeResponse(null, userType.getName());
    }

    public static UserTypeResponse toAdminResponse(UserType userType) {
        return new UserTypeResponse(userType.getId(), userType.getName());
    }
}
