package com.example.tech_challenge.component.mapper;

import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.dto.request.UserRequest;
import com.example.tech_challenge.domain.user.dto.response.LoginUserResponse;
import com.example.tech_challenge.domain.user.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UserMapper {

    private final AddressMapper addressMapper;

    public User toUserEntity(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        user.setLogin(createUserRequest.getLogin());
        user.setAddress(Objects.isNull(createUserRequest.getAddress()) ? null : addressMapper.toAddressEntity(createUserRequest.getAddress()));
        user.setPassword(createUserRequest.getPassword());
        user.setAuthority(createUserRequest.getAuthority());
        return user;
    }

    public User toUserEntity(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setLogin(userRequest.getLogin());
        user.setAddress(Objects.isNull(userRequest.getAddress()) ? null : addressMapper.toAddressEntity(userRequest.getAddress()));
        return user;
    }

    public User toUserEntity(UpdateUserPasswordRequest updateUserPasswordRequest) {
        User user = new User();
        user.setPassword(updateUserPasswordRequest.getNewPassword());
        return user;
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
