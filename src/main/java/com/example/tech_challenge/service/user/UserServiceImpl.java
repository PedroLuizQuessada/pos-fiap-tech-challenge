package com.example.tech_challenge.service.user;

import com.example.tech_challenge.mapper.entity.UserMapper;
import com.example.tech_challenge.domain.user.entity.User;
import com.example.tech_challenge.domain.user.entity.UserDB;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserRequest;
import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.exception.EmailAlreadyInUseException;
import com.example.tech_challenge.exception.LoginAlreadyInUseException;
import com.example.tech_challenge.exception.AdminCreationNotAllowedException;
import com.example.tech_challenge.exception.UserNotFoundException;
import com.example.tech_challenge.repo.UserRepository;
import com.example.tech_challenge.service.address.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressService addressService;

    @Override
    public User create(CreateUserRequest createUserRequest, boolean allowAdmin) {

        User createUser = userMapper.toUserEntity(createUserRequest);

        if (!allowAdmin && AuthorityEnum.ADMIN.equals(createUser.getAuthority()))
            throw new AdminCreationNotAllowedException();

        checkEmailAlreadyInUse(createUser.getEmail());
        checkLoginAlreadyInUse(createUser.getLogin());

        return userRepository.save(createUser.toEntityDB()).toEntity();
    }

    @Override
    public void update(UpdateUserRequest updateUserRequest, Long id) {
        User updateUserOld = getUserById(id);
        update(updateUserRequest, updateUserOld);
    }

    @Override
    public void update(UpdateUserRequest updateUserRequest, User updateUserOld) {
        User userEntity = userMapper.toUserEntity(updateUserRequest, updateUserOld);
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

    @Override
    public void delete(Long id) {
        User deleteUser = getUserById(id);
        userRepository.delete(deleteUser.toEntityDB());
    }

    @Override
    public void updatePassword(UpdateUserPasswordRequest updateUserPasswordRequest, User updatePasswordUser) {
        updatePasswordUser = userMapper.toUserEntity(updateUserPasswordRequest, updatePasswordUser);
        userRepository.save(updatePasswordUser.toEntityDB());
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
