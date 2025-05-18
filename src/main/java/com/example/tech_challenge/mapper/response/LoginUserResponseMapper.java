package com.example.tech_challenge.mapper.response;

import com.example.tech_challenge.domain.user.dto.response.LoginUserResponse;
import com.example.tech_challenge.domain.user.entity.User;

public class LoginUserResponseMapper implements ResponseMapper<LoginUserResponse, User> {
    @Override
    public LoginUserResponse map(User user) {
        return new LoginUserResponse(user.getName());
    }
}
