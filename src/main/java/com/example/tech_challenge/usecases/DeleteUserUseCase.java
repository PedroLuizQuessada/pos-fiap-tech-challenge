package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.mappers.UserMapper;

import java.util.Objects;

public class DeleteUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;

    public DeleteUserUseCase(UserGateway userGateway, AddressGateway addressGateway) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
    }

    public void execute(String login) {
        User user = userGateway.findUserByLogin(login);
        deleteUser(UserMapper.toDto(user));
    }

    public void execute(Long id) {
        User user = userGateway.findUserById(id);
        deleteUser(UserMapper.toDto(user));
    }

    private void deleteUser(UserDto userDto) {
        userGateway.deleteUser(userDto);
        if (!Objects.isNull(userDto.address()))
            addressGateway.delete(userDto.address());
    }
}
