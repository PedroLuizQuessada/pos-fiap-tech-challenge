package com.example.tech_challenge.presenters;

import com.example.tech_challenge.dtos.responses.LoginResponse;
import com.example.tech_challenge.entities.User;

public class LoginPresenter {

    public static LoginResponse toResponse(User user) {
        return new LoginResponse(user.getLogin(), user.getPassword(), user.getUserType().getName());
    }
}
