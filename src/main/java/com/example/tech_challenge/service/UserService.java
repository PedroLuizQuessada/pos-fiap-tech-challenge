package com.example.tech_challenge.service;

import com.example.tech_challenge.component.PasswordComponent;
import com.example.tech_challenge.domain.user.request.NewUserRequest;
import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.domain.user.UserResponse;
import com.example.tech_challenge.domain.user.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.request.UpdateUserRequest;
import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.exception.EmailAlreadyInUseException;
import com.example.tech_challenge.exception.LoginAlreadyInUseException;
import com.example.tech_challenge.exception.UnauthorizedActionException;
import com.example.tech_challenge.exception.UserNotFoundException;
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

        checkEmailAlreadyInUse(newUser.getEmail());
        checkLoginAlreadyInUse(newUser.getLogin());

        return userRepository.save(newUser).entityToResponse();
    }

    public void update(UserDetails clientUserDetails, UpdateUserRequest updateUserRequest) {
        User updateUser = new User(updateUserRequest.getName(), updateUserRequest.getEmail(), updateUserRequest.getLogin(),
                updateUserRequest.getAddress());

        checkAdminOrSameUser(customUserDetailsService.getAuthority(String.valueOf(clientUserDetails.getAuthorities().stream().findFirst())),
                clientUserDetails.getUsername(), updateUser.getLogin(), "atualizar outro usuário");

        checkLoginExists(updateUser.getLogin());

        if (!Objects.equals(updateUserRequest.getOldEmail(), updateUserRequest.getEmail())) {
            checkEmailAlreadyInUse(updateUser.getEmail());
        }
        if (!Objects.equals(updateUserRequest.getOldLogin(), updateUserRequest.getLogin())) {
            checkLoginAlreadyInUse(updateUser.getLogin());
        }

        userRepository.updateByLogin(updateUser.getName(), updateUser.getEmail(), updateUser.getLogin(),
                updateUser.getAddress(), updateUser.getLastUpdateDate(), updateUserRequest.getOldLogin());
    }

    public void delete(UserDetails clientUserDetails, String login) {

        checkAdminOrSameUser(customUserDetailsService.getAuthority(String.valueOf(clientUserDetails.getAuthorities().stream().findFirst())),
                clientUserDetails.getUsername(), login, "deletar outro usuário");

        checkLoginExists(login);

        userRepository.deleteByLogin(login);
    }

    public void updatePassword(UserDetails clientUserDetails, UpdateUserPasswordRequest updateUserPasswordRequest) {
        userRepository.updatePasswordByLogin(passwordComponent.encode(updateUserPasswordRequest.getNewPassword()), clientUserDetails.getUsername());
    }

    private void checkAdminOrSameUser(AuthorityEnum clientUserAuthority, String clientUserLogin, String login, String action) {
        if (!AuthorityEnum.ADMIN.equals(clientUserAuthority)
                && !Objects.equals(clientUserLogin, login)) {
            throw new UnauthorizedActionException(action, clientUserLogin);
        }
    }

    private void checkLoginExists(String login) {
        if (userRepository.countUserByLoginEquals(login).equals(0)) {
            throw new UserNotFoundException(login);
        }
    }

    private void checkEmailAlreadyInUse(String email) {
        if (userRepository.countUserByEmailEquals(email) > 0) {
            throw new EmailAlreadyInUseException();
        }
    }

    private void checkLoginAlreadyInUse(String login) {
        if (userRepository.countUserByLoginEquals(login) > 0) {
            throw new LoginAlreadyInUseException();
        }
    }
}
