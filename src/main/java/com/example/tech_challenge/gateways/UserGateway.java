package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.exceptions.UserNotFoundException;

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

        return createEntity(userDtoOptional.get());
    }

    public Long countByEmail(String email) {
        return userDataSource.countByEmail(email);
    }

    public Long countByLogin(String login) {
        return userDataSource.countByLogin(login);
    }

    public User createUser(UserDto createUserDto) {
        UserDto userDto = userDataSource.createUser(createUserDto);
        return createEntity(userDto);
    }

    public User findUserById(Long id) {
        if (Objects.isNull(id))
            throw new UserNotFoundException();

        Optional<UserDto> userDtoOptional = userDataSource.findUserById(id);

        if (userDtoOptional.isEmpty())
            throw new UserNotFoundException();

        return createEntity(userDtoOptional.get());
    }

    public User updateUser(UserDto updateUserDto) {
        UserDto userDto = userDataSource.updateUser(updateUserDto);
        return createEntity(userDto);
    }

    public void deleteUser(UserDto userDto) {
        userDataSource.deleteUser(userDto);
    }

    private User createEntity(UserDto userDto) {
        Address address = null;
        if (!Objects.isNull(userDto.addressDto()))
            address = new Address(userDto.addressDto().id(), userDto.addressDto().state(), userDto.addressDto().city(),
                    userDto.addressDto().street(), userDto.addressDto().number(), userDto.addressDto().zipCode(),
                    userDto.addressDto().aditionalInfo());

        UserType userType = null;
        if (!Objects.isNull(userDto.userTypeDto()))
            userType = new UserType(userDto.userTypeDto().id(), userDto.userTypeDto().name());

        return new User(userDto.id(), userDto.name(), userDto.email(), userDto.login(), userDto.password(), userDto.lastUpdateDate(),
                address, userType, false);
    }
}
