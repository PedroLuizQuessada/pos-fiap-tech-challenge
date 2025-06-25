package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.UserDto;

import java.util.Optional;

public interface UserDataSource {
    Optional<UserDto> findUserByLoginAndPassword(String login, String password);
    UserDto createUser(UserDto userDto);
    Long countByEmail(String email);
    Long countByLogin(String login);
    Optional<UserDto> findUserById(Long id);
    UserDto updateUser(UserDto userDto);
    void deleteUser(UserDto userDto);
}
