package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.UserGateway;

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
        AddressDto addressDto = getAddressDto(user);
        UserTypeDto userTypeDto = getUserTypeDto(user);
        deleteUser(new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(), user.getLastUpdateDate(),
                addressDto, userTypeDto));
    }

    public void execute(Long id) {
        User user = userGateway.findUserById(id);
        AddressDto addressDto = getAddressDto(user);
        UserTypeDto userTypeDto = getUserTypeDto(user);
        deleteUser(new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(), user.getLastUpdateDate(),
                addressDto, userTypeDto));
    }

    private AddressDto getAddressDto(User user) {
        AddressDto addressDto = null;
        if (!Objects.isNull(user.getAddress()))
            addressDto = new AddressDto(user.getAddress().getId(), user.getAddress().getState(), user.getAddress().getCity(),
                    user.getAddress().getStreet(), user.getAddress().getNumber(), user.getAddress().getZipCode(), user.getAddress().getAditionalInfo());
        return addressDto;
    }

    private UserTypeDto getUserTypeDto(User user) {
        UserTypeDto userTypeDto = null;
        if (!Objects.isNull(user.getUserType()))
            userTypeDto = new UserTypeDto(user.getUserType().getId(), user.getUserType().getName());
        return userTypeDto;
    }

    private void deleteUser(UserDto userDto) {
        userGateway.deleteUser(userDto);
        if (!Objects.isNull(userDto.addressDto()))
            addressGateway.delete(userDto.addressDto());
    }
}
