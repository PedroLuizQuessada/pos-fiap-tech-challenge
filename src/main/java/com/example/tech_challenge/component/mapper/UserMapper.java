package com.example.tech_challenge.component.mapper;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.user.entity.User;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.dto.request.UserRequest;
import com.example.tech_challenge.domain.user.dto.response.LoginUserResponse;
import com.example.tech_challenge.domain.user.dto.response.UserResponse;
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

    public User toUserEntity(UserRequest userRequest, Long id, String password, AuthorityEnum authority, Long addressId) {
        Address address = Objects.isNull(userRequest.getAddress()) ? null : addressMapper.toAddressEntity(userRequest.getAddress(), addressId);
        return new User(id, userRequest.getName(), userRequest.getEmail(), userRequest.getLogin(), password,
                address, authority);
    }

    public User toUserEntity(UpdateUserPasswordRequest updateUserPasswordRequest, Long id) {
        return new User(id, updateUserPasswordRequest.getNewPassword());
    }

    public LoginUserResponse toLoginUserResponse(User user) {
        return new LoginUserResponse(user.getName());
    }

    public UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setLogin(user.getLogin());
        userResponse.setLastUpdateDate(user.getLastUpdateDate());
        userResponse.setAddress(Objects.isNull(user.getAddress()) ? null : addressMapper.toAddressResponse(user.getAddress()));
        userResponse.setAuthority(user.getAuthority());
        return userResponse;
    }
}
