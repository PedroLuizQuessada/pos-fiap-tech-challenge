package com.example.tech_challenge.presenters;

import com.example.tech_challenge.dtos.response.LoginUserResponse;
import com.example.tech_challenge.entities.User;

public class LoginUserPresenter {

    public static LoginUserResponse toResponse(User user) {
        return new LoginUserResponse(user.getName());
    }
}
