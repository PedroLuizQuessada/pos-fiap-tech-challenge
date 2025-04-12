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
        User newUser = newUserRequest.requestToEntity();
        newUser.setPassword(passwordComponent.encode(newUser.getPassword()));

        checkEmailAlreadyInUse(newUser.getEmail());
        checkLoginAlreadyInUse(newUser.getLogin());

        return userRepository.save(newUser).entityToResponse();
    }

    public void update(UserDetails clientUserDetails, UpdateUserRequest updateUserRequest) {
        User updateUser = updateUserRequest.requestToEntity();

        checkAdminOrSameUser(customUserDetailsService.getAuthority(String.valueOf(clientUserDetails.getAuthorities().stream().findFirst())),
                clientUserDetails.getUsername(), updateUserRequest.getOldLogin(), "atualizar outro usuário");

        User updateUserOld = getUserByLogin(updateUserRequest.getOldLogin());
        updateUser.setId(updateUserOld.getId());
        updateUser.setPassword(updateUserOld.getPassword());
        updateUser.setAuthority(updateUserOld.getAuthority());
        if (!Objects.isNull(updateUserOld.getAddress()))
            updateUser.getAddress().setId(updateUserOld.getAddress().getId());
        updateUser.getAddress().setUser(updateUser);

        if (!Objects.equals(updateUserRequest.getOldEmail(), updateUser.getEmail())) {
            checkEmailAlreadyInUse(updateUser.getEmail());
        }
        if (!Objects.equals(updateUserRequest.getOldLogin(), updateUser.getLogin())) {
            checkLoginAlreadyInUse(updateUser.getLogin());
        }

        userRepository.save(updateUser);
    }

    public void delete(UserDetails clientUserDetails, String login) {

        checkAdminOrSameUser(customUserDetailsService.getAuthority(String.valueOf(clientUserDetails.getAuthorities().stream().findFirst())),
                clientUserDetails.getUsername(), login, "deletar outro usuário");

        User deleteUser = getUserByLogin(login);

        userRepository.delete(deleteUser);
    }

    public void updatePassword(UserDetails clientUserDetails, UpdateUserPasswordRequest updateUserPasswordRequest) {
        User updatePasswordUser = updateUserPasswordRequest.requestToEntity();
        userRepository.updatePasswordByLogin(passwordComponent.encode(updatePasswordUser.getPassword()), clientUserDetails.getUsername());
    }

    private void checkAdminOrSameUser(AuthorityEnum clientUserAuthority, String clientUserLogin, String login, String action) {
        if (!AuthorityEnum.ADMIN.equals(clientUserAuthority)
                && !Objects.equals(clientUserLogin, login)) {
            throw new UnauthorizedActionException(action, clientUserLogin);
        }
    }

    private User getUserByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException(login);
        }
        return user;
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
