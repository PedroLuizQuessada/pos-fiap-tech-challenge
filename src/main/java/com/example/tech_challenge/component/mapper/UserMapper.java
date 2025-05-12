package com.example.tech_challenge.component.mapper;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.user.entity.User;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.dto.request.UserRequest;
import com.example.tech_challenge.enums.AuthorityEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UserMapper {

    private final AddressMapper addressMapper;

    public User toUserEntity(CreateUserRequest createUserRequest) {
        Address address = Objects.isNull(createUserRequest.getAddress()) ? null : addressMapper.toAddressEntity(createUserRequest.getAddress());
        return new User(createUserRequest.getName(), createUserRequest.getEmail(),
                createUserRequest.getLogin(), createUserRequest.getPassword(), address, createUserRequest.getAuthority());
    }

    public User toUserEntity(UserRequest userRequest, Long id, String password, AuthorityEnum authority, Address oldAddress) {
        Long oldAddressId = null;
        if (!Objects.isNull(oldAddress))
            oldAddressId = oldAddress.getId();
        Address address = Objects.isNull(userRequest.getAddress()) ? null : addressMapper.toAddressEntity(userRequest.getAddress(), oldAddressId);
        return new User(id, userRequest.getName(), userRequest.getEmail(), userRequest.getLogin(), password,
                address, authority, false);
    }

    public User toUserEntity(UpdateUserPasswordRequest updateUserPasswordRequest, User user) {
        return new User(user.getId(), user.getName(), user.getEmail(), user.getLogin(), updateUserPasswordRequest.getNewPassword(),
                user.getAddress(), user.getAuthority(), true);
    }
}
