package com.example.tech_challenge.infraestructure.security;

import com.example.tech_challenge.controllers.UserController;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.response.LoginUserResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserController userController;

    public UserDetailsServiceImpl(UserDataSource userDataSource) {
        this.userController = new UserController(userDataSource, null, null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LoginUserResponse userResponse = userController.login(username);
        return new org.springframework.security.core.userdetails.User(userResponse.login(), userResponse.password(),
                List.of(new SimpleGrantedAuthority(userResponse.authority().name())));
    }
}
