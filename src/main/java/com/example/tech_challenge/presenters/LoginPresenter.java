package com.example.tech_challenge.presenters;

import com.example.tech_challenge.dtos.response.LoginTokenResponse;
import com.example.tech_challenge.dtos.response.LoginUserResponse;
import com.example.tech_challenge.entities.User;

public class LoginPresenter {

    public static LoginTokenResponse toResponse(String token) {
        return new LoginTokenResponse(token);
    }

    public static LoginUserResponse toResponse(User user) {
        return new LoginUserResponse(user.getLogin(), user.getPassword(), user.getAuthority());
    }
}
