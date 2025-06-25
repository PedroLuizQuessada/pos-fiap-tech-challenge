package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.exception.UserNotFoundException;

import java.util.Objects;
import java.util.Optional;

public class UserGateway {

    private final UserDataSource userDataSource;

    public UserGateway(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public User findUserByLoginAndPassword(String login, String password) {
        Optional<UserDto> userDtoOptional = userDataSource.findUserByLoginAndPassword(login, password);

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
        Optional<UserDto> userDtoOptional = userDataSource.findUserById(id);

        if (userDtoOptional.isEmpty())
            throw new UserNotFoundException();

        return createEntity(userDtoOptional.get());
    }

    public User updateUser(UserDto updateUserDto) {
        UserDto userDto = userDataSource.updateUser(updateUserDto);
        return createEntity(userDto);
    }

    public void deleteUserById(Long id) {
        User userDto = findUserById(id);
        userDataSource.deleteUser(createDto(userDto));
    }

    private User createEntity(UserDto userDto) {
        Address address = null;
        if (!Objects.isNull(userDto.addressDto()))
            address = new Address(userDto.addressDto().id(), userDto.addressDto().state(), userDto.addressDto().city(),
                    userDto.addressDto().street(), userDto.addressDto().number(), userDto.addressDto().zipCode(),
                    userDto.addressDto().aditionalInfo());

        return new User(userDto.id(), userDto.name(), userDto.email(), userDto.login(), userDto.password(), userDto.lastUpdateDate(),
                address, userDto.authority(), false);
    }

    private UserDto createDto(User user) {
        AddressDto addressDto = null;
        if (!Objects.isNull(user.getAddress()))
            addressDto = new AddressDto(user.getAddress().getId(), user.getAddress().getState(), user.getAddress().getCity(),
                    user.getAddress().getStreet(), user.getAddress().getNumber(), user.getAddress().getZipCode(),
                    user.getAddress().getAditionalInfo());

        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(), user.getLastUpdateDate(),
                addressDto, user.getAuthority());
    }
}
