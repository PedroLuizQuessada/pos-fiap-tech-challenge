package com.example.tech_challenge.mapper.entity;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserRequest;
import com.example.tech_challenge.domain.user.entity.User;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.exception.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@AllArgsConstructor
public class UserMapper implements EntityMapper<User> {

    private final Validator validator;

    private final AddressMapper addressMapper;

    public User toUserEntity(CreateUserRequest createUserRequest) throws ConstraintViolationException {
        Address address = Objects.isNull(createUserRequest.address()) ? null : addressMapper.toAddressEntity(createUserRequest.address());
        User user = new User(createUserRequest.name(), createUserRequest.email(),
                createUserRequest.login(), createUserRequest.password(), address, createUserRequest.authority());

        validateEntityWithDecodedPassword(user);

        return user;
    }

    public User toUserEntity(UpdateUserRequest updateUserRequest, User user) throws ConstraintViolationException {
        Long oldAddressId = null;
        if (!Objects.isNull(user.getAddress()))
            oldAddressId = user.getAddress().getId();
        Address address = Objects.isNull(updateUserRequest.address()) ? null : addressMapper.toAddressEntity(updateUserRequest.address(), oldAddressId);
        User userReturn = new User(user.getId(), updateUserRequest.name(), updateUserRequest.email(), updateUserRequest.login(), user.getEncodedPassword(),
                address, user.getAuthority(), false);

        this.validateEntity(userReturn);

        return userReturn;
    }

    public User toUserEntity(UpdateUserPasswordRequest updateUserPasswordRequest, User user) throws ConstraintViolationException {
        User userReturn = new User(user.getId(), user.getName(), user.getEmail(), user.getLogin(), updateUserPasswordRequest.newPassword(),
                user.getAddress(), user.getAuthority(), true);

        validateEntityWithDecodedPassword(userReturn);

        return userReturn;
    }

    private void validateEntityWithDecodedPassword(User user) {
        List<String> constraintsMessages = new ArrayList<>();

        try {
            user.validateDecodedPassword();
        }
        catch (ConstraintViolationException e) {
            constraintsMessages.add(e.getMessage());
        }

        try {
            this.validateEntity(user);
        }
        catch (ConstraintViolationException e) {
            constraintsMessages.add(e.getMessage());
        }

        if (!constraintsMessages.isEmpty()) {
            throw new ConstraintViolationException(constraintsMessages);
        }
    }

    @Override
    public void validateEntity(User entity) throws ConstraintViolationException {
        Set<ConstraintViolation<User>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            List<String> constraintsMessages = new ArrayList<>();
            violations.forEach(action -> constraintsMessages.add(action.getMessage()));
            throw new ConstraintViolationException(constraintsMessages);
        }
    }
}
