package com.example.tech_challenge.infraestructure.services;

import com.example.tech_challenge.controllers.LoginController;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.response.LoginResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginController loginController;

    public UserDetailsServiceImpl(UserDataSource userDataSource) {
        this.loginController = new LoginController(userDataSource);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LoginResponse userResponse = loginController.login(username);
        return new org.springframework.security.core.userdetails.User(userResponse.login(), userResponse.password(),
                List.of(new SimpleGrantedAuthority(userResponse.authority().name())));
    }
}
