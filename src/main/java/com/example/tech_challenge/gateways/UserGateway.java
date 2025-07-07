package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.exceptions.UserNotFoundException;
import com.example.tech_challenge.mappers.UserMapper;

import java.util.Objects;
import java.util.Optional;

public class UserGateway {

    private final UserDataSource userDataSource;

    public UserGateway(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public User findUserByLogin(String login) {
        Optional<UserDto> userDtoOptional = userDataSource.findUserByLogin(login);

        if (userDtoOptional.isEmpty())
            throw new UserNotFoundException();

        return UserMapper.toEntity(userDtoOptional.get(), false);
    }

    public Long countByEmail(String email) {
        return userDataSource.countByEmail(email);
    }

    public Long countByLogin(String login) {
        return userDataSource.countByLogin(login);
    }

    public User createUser(UserDto createUserDto) {
        UserDto userDto = userDataSource.createUser(createUserDto);
        return UserMapper.toEntity(userDto, false);
    }

    public User findUserById(Long id) {
        if (Objects.isNull(id))
            throw new UserNotFoundException();

        Optional<UserDto> userDtoOptional = userDataSource.findUserById(id);

        if (userDtoOptional.isEmpty())
            throw new UserNotFoundException();

        return UserMapper.toEntity(userDtoOptional.get(), false);
    }

    public User updateUser(UserDto updateUserDto) {
        UserDto userDto = userDataSource.updateUser(updateUserDto);
        return UserMapper.toEntity(userDto, false);
    }

    public void deleteUser(UserDto userDto) {
        userDataSource.deleteUser(userDto);
    }

    public Long countByUserType(Long userTypeId) {
        return userDataSource.countByUserType(userTypeId);
    }
}
