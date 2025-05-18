package com.example.tech_challenge.mapper.response;

import com.example.tech_challenge.domain.user.dto.response.UserResponse;
import com.example.tech_challenge.domain.user.entity.User;

import java.util.Objects;

public class UserResponseMapper implements ResponseMapper<UserResponse, User> {
    @Override
    public UserResponse map(User user) {
        AddressResponseMapper addressResponseMapper = new AddressResponseMapper();
        return new UserResponse(user.getName(), user.getEmail(), user.getLogin(), user.getLastUpdateDate(),
                Objects.isNull(user.getAddress()) ? null : addressResponseMapper.map(user.getAddress()), user.getAuthority());
    }
}
