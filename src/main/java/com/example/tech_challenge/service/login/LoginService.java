package com.example.tech_challenge.service.login;

import com.example.tech_challenge.domain.user.entity.User;

public interface LoginService {
    User login(String authToken, boolean onlyAdmin);
}
