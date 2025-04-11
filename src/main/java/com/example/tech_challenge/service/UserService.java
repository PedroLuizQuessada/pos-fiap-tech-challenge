package com.example.tech_challenge.service;

import com.example.tech_challenge.component.PasswordComponent;
import com.example.tech_challenge.domain.user.request.NewUserRequest;
import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.domain.user.UserResponse;
import com.example.tech_challenge.domain.user.request.UpdateUserRequest;
import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.exception.EmailAlreadyInUseException;
import com.example.tech_challenge.exception.LoginAlreadyInUseException;
import com.example.tech_challenge.exception.UnauthorizedActionException;
import com.example.tech_challenge.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordComponent passwordComponent;
    private final CustomUserDetailsService customUserDetailsService;

    public UserService(UserRepository userRepository, PasswordComponent passwordComponent, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordComponent = passwordComponent;
        this.customUserDetailsService = customUserDetailsService;
    }

    public UserResponse create(NewUserRequest newUserRequest) {
        User newUser = new User(newUserRequest.getName(), newUserRequest.getEmail(), newUserRequest.getLogin(),
                passwordComponent.encode(newUserRequest.getPassword()), newUserRequest.getAddress(), newUserRequest.getAuthority());

        checkEmail(newUser.getEmail());
        checkLogin(newUser.getLogin());

        return userRepository.save(newUser).entityToResponse();
    }

    public void update(UserDetails clientUserDetails, UpdateUserRequest updateUserRequest) {
        User updateUser = new User(updateUserRequest.getName(), updateUserRequest.getEmail(), updateUserRequest.getLogin(),
                updateUserRequest.getAddress());

        if (!AuthorityEnum.ADMIN.equals(customUserDetailsService.getAuthority(String.valueOf(clientUserDetails.getAuthorities().stream().findFirst())))
            && !Objects.equals(clientUserDetails.getUsername(), updateUserRequest.getOldLogin())) {
            throw new UnauthorizedActionException("atualizar outro usuário", clientUserDetails.getUsername());
        }

        if (!Objects.equals(updateUserRequest.getOldEmail(), updateUserRequest.getEmail())) {
            checkEmail(updateUser.getEmail());
        }
        if (!Objects.equals(updateUserRequest.getOldLogin(), updateUserRequest.getLogin())) {
            checkLogin(updateUser.getLogin());
        }

        userRepository.updateByLogin(updateUser.getName(), updateUser.getEmail(), updateUser.getLogin(),
                updateUser.getAddress(), updateUser.getLastUpdateDate(), updateUserRequest.getOldLogin());
    }

    private void checkEmail(String email) {
        if (userRepository.countUserByEmailEquals(email) > 0) {
            throw new EmailAlreadyInUseException();
        }
    }

    private void checkLogin(String login) {
        if (userRepository.countUserByLoginEquals(login) > 0) {
            throw new LoginAlreadyInUseException();
        }
    }
}
