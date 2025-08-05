package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.dtos.requests.AdminCreateUserRequest;
import com.example.tech_challenge.dtos.requests.CreateUserRequest;
import com.example.tech_challenge.dtos.responses.UserResponse;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;

import java.util.Objects;

public class UserMapper {

    private UserMapper() {}

    public static User toEntity(UserDto userDto, boolean encodePassword) {
        return new User(userDto.id(), userDto.name(), userDto.email(), userDto.login(), userDto.password(), userDto.lastUpdateDate(),
                !Objects.isNull(userDto.address()) ? AddressMapper.toEntity(userDto.address()) : null,
                !Objects.isNull(userDto.userType()) ? UserTypeMapper.toEntity(userDto.userType()) : null,
                encodePassword);
    }

    public static User toEntity(CreateUserRequest userDto, UserType userType, boolean encodePassword) {
        return new User(null, userDto.name(), userDto.email(), userDto.login(), userDto.password(), null,
                !Objects.isNull(userDto.address()) ? AddressMapper.toEntity(userDto.address()) : null, userType,
                encodePassword);
    }

    public static User toEntity(AdminCreateUserRequest userDto, UserType userType, boolean encodePassword) {
        return new User(null, userDto.name(), userDto.email(), userDto.login(), userDto.password(), null,
                !Objects.isNull(userDto.address()) ? AddressMapper.toEntity(userDto.address()) : null, userType,
                encodePassword);
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(),
                user.getLastUpdateDate(),
                !Objects.isNull(user.getAddress()) ? AddressMapper.toDto(user.getAddress()) : null,
                !Objects.isNull(user.getUserType()) ? UserTypeMapper.toDto(user.getUserType()) : null);
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(null, user.getName(), user.getEmail(), user.getLogin(), user.getLastUpdateDate(),
                !Objects.isNull(user.getAddress()) ? AddressMapper.toResponse(user.getAddress()) : null,
                !Objects.isNull(user.getUserType()) ? UserTypeMapper.toResponse(user.getUserType()) : null);
    }

    public static UserResponse toAdminResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getLastUpdateDate(),
                !Objects.isNull(user.getAddress()) ? AddressMapper.toAdminResponse(user.getAddress()) : null,
                !Objects.isNull(user.getUserType()) ? UserTypeMapper.toAdminResponse(user.getUserType()) : null);
    }
}
