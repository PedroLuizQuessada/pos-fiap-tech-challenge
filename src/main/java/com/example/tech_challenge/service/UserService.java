package com.example.tech_challenge.service;

import com.example.tech_challenge.component.security.PasswordComponent;
import com.example.tech_challenge.component.mapper.UserMapper;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.dto.request.UserRequest;
import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.exception.EmailAlreadyInUseException;
import com.example.tech_challenge.exception.LoginAlreadyInUseException;
import com.example.tech_challenge.exception.UnauthorizedActionException;
import com.example.tech_challenge.exception.UserNotFoundException;
import com.example.tech_challenge.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordComponent passwordComponent;
    private final AddressService addressService;

    public User create(UserDetails clientUserDetails, CreateUserRequest createUserRequest) {
        if (Objects.isNull(clientUserDetails) && AuthorityEnum.ADMIN.equals(createUserRequest.getAuthority())) {
            throw new UnauthorizedActionException("usuário não autenticado criar usuário admin");
        }

        User createUser = userMapper.toUserEntity(createUserRequest);

        checkEmailAlreadyInUse(createUser.getEmail());
        checkLoginAlreadyInUse(createUser.getLogin());

        createUser.setPassword(passwordComponent.encode(createUser.getPassword()));

        return userRepository.save(createUser);
    }

    public void update(UserRequest userRequest, String login) {
        User updateUserOld = getUserByLogin(login);
        update(userRequest, updateUserOld);
    }

    public void update(UserRequest userRequest, Long id) {
        User updateUserOld = getUserById(id);
        update(userRequest, updateUserOld);
    }

    public void delete(String login) {
        User deleteUser = getUserByLogin(login);
        userRepository.delete(deleteUser);
    }

    public void delete(Long id) {
        User deleteUser = getUserById(id);
        userRepository.delete(deleteUser);
    }

    public void updatePassword(HttpSession httpSession, String login, UpdateUserPasswordRequest updateUserPasswordRequest) {
        User updatePasswordUser = userMapper.toUserEntity(updateUserPasswordRequest);
        updatePasswordUser.setPassword(passwordComponent.encode(updatePasswordUser.getPassword()));

        userRepository.updatePasswordByLogin(updatePasswordUser.getPassword(), login);
        httpSession.invalidate();
    }

    public User getUserByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }
        return user;
    }

    private User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return user.get();
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

    private void update(UserRequest userRequest, User updateUserOld) {
        User updateUser = userMapper.toUserEntity(userRequest);
        Integer updateUserOldAddressId = !Objects.isNull(updateUserOld.getAddress()) ? updateUserOld.getAddress().getId() : null;

        updateUser.setId(updateUserOld.getId());
        updateUser.setPassword(updateUserOld.getPassword());
        updateUser.setAuthority(updateUserOld.getAuthority());
        if (!Objects.isNull(updateUser.getAddress())) {
            if (!Objects.isNull(updateUserOld.getAddress()))
                updateUser.getAddress().setId(updateUserOld.getAddress().getId());
            updateUser.getAddress().setUser(updateUser);
        }

        if (!Objects.equals(updateUserOld.getEmail(), updateUser.getEmail())) {
            checkEmailAlreadyInUse(updateUser.getEmail());
        }
        if (!Objects.equals(updateUserOld.getLogin(), updateUser.getLogin())) {
            checkLoginAlreadyInUse(updateUser.getLogin());
        }

        userRepository.save(updateUser);
        if (Objects.isNull(updateUser.getAddress()) && !Objects.isNull(updateUserOldAddressId))
            addressService.deleteById(updateUserOldAddressId);
    }
}
