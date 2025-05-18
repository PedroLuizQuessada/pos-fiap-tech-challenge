package com.example.tech_challenge.mapper.entity;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserRequest;
import com.example.tech_challenge.domain.user.entity.User;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UserMapper {

    private final AddressMapper addressMapper;

    public User toUserEntity(CreateUserRequest createUserRequest) {
        Address address = Objects.isNull(createUserRequest.address()) ? null : addressMapper.toAddressEntity(createUserRequest.address());
        return new User(createUserRequest.name(), createUserRequest.email(),
                createUserRequest.login(), createUserRequest.password(), address, createUserRequest.authority());
    }

    public User toUserEntity(UpdateUserRequest updateUserRequest, User user) {
        Long oldAddressId = null;
        if (!Objects.isNull(user.getAddress()))
            oldAddressId = user.getAddress().getId();
        Address address = Objects.isNull(updateUserRequest.address()) ? null : addressMapper.toAddressEntity(updateUserRequest.address(), oldAddressId);
        return new User(user.getId(), updateUserRequest.name(), updateUserRequest.email(), updateUserRequest.login(), user.getEncodedPassword(),
                address, user.getAuthority(), false);
    }

    public User toUserEntity(UpdateUserPasswordRequest updateUserPasswordRequest, User user) {
        return new User(user.getId(), user.getName(), user.getEmail(), user.getLogin(), updateUserPasswordRequest.newPassword(),
                user.getAddress(), user.getAuthority(), true);
    }
}
