package com.example.tech_challenge.service;

import com.example.tech_challenge.component.mapper.UserMapper;
import com.example.tech_challenge.domain.user.entity.User;
import com.example.tech_challenge.domain.user.entity.UserDB;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.dto.request.UserRequest;
import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.exception.EmailAlreadyInUseException;
import com.example.tech_challenge.exception.LoginAlreadyInUseException;
import com.example.tech_challenge.exception.AdminCreationNotAllowedException;
import com.example.tech_challenge.exception.UserNotFoundException;
import com.example.tech_challenge.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressService addressService;

    public User create(CreateUserRequest createUserRequest, boolean allowAdmin) {

        User createUser = userMapper.toUserEntity(createUserRequest);

        if (!allowAdmin && AuthorityEnum.ADMIN.equals(createUser.getAuthority()))
            throw new AdminCreationNotAllowedException();

        checkEmailAlreadyInUse(createUser.getEmail());
        checkLoginAlreadyInUse(createUser.getLogin());

        return userRepository.save(createUser.toEntityDB()).toEntity();
    }

    public void update(UserRequest userRequest, Long id) {
        User updateUserOld = getUserById(id);
        update(userRequest, updateUserOld);
    }

    public void update(UserRequest userRequest, User updateUserOld) {
        User userEntity = userMapper.toUserEntity(userRequest, updateUserOld.getId(), updateUserOld.getEncodedPassword(),
                updateUserOld.getAuthority(), updateUserOld.getAddress());
        Long updateUserOldAddressId = !Objects.isNull(updateUserOld.getAddress()) ? updateUserOld.getAddress().getId() : null;

        if (!Objects.equals(updateUserOld.getEmail(), userEntity.getEmail())) {
            checkEmailAlreadyInUse(userEntity.getEmail());
        }
        if (!Objects.equals(updateUserOld.getLogin(), userEntity.getLogin())) {
            checkLoginAlreadyInUse(userEntity.getLogin());
        }

        userRepository.save(userEntity.toEntityDB());
        if (Objects.isNull(userEntity.getAddress()) && !Objects.isNull(updateUserOldAddressId))
            addressService.deleteById(Math.toIntExact(updateUserOldAddressId));
    }

    public void delete(Long id) {
        User deleteUser = getUserById(id);
        userRepository.delete(deleteUser.toEntityDB());
    }

    public void updatePassword(UpdateUserPasswordRequest updateUserPasswordRequest, Long id) {
        User updatePasswordUser = getUserById(id);
        updatePasswordUser = userMapper.toUserEntity(updateUserPasswordRequest, updatePasswordUser);
        userRepository.save(updatePasswordUser.toEntityDB());
    }

    public User getUserByLoginAndPassword(String login, String password) {
        Optional<UserDB> userDB = userRepository.findByLoginAndPassword(login, password);
        if (userDB.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userDB.get().toEntity();
    }

    private User getUserById(Long id) {
        Optional<UserDB> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return user.get().toEntity();
    }

    private void checkEmailAlreadyInUse(String email) {
        if (userRepository.countByEmail(email) > 0) {
            throw new EmailAlreadyInUseException();
        }
    }

    private void checkLoginAlreadyInUse(String login) {
        if (userRepository.countByLogin(login) > 0) {
            throw new LoginAlreadyInUseException();
        }
    }
}
