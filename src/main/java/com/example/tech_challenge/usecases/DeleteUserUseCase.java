package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.gateways.UserGateway;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

public class DeleteUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;
    private final TokenGateway tokenGateway;

    public DeleteUserUseCase(UserGateway userGateway, AddressGateway addressGateway, TokenGateway tokenGateway) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
        this.tokenGateway = tokenGateway;
    }

    public String execute(UserDetails userDetails, String token) {
        String login = (!Objects.isNull(userDetails)) ? userDetails.getUsername() : tokenGateway.getTokenUsername(token);

        User user = userGateway.findUserByLogin(login);
        AddressDto addressDto = getAddressDto(user);
        UserTypeDto userTypeDto = getUserTypeDto(user);
        return deleteUser(new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(), user.getLastUpdateDate(),
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

    private String deleteUser(UserDto userDto) {
        userGateway.deleteUser(userDto);
        if (!Objects.isNull(userDto.addressDto()))
            addressGateway.delete(userDto.addressDto());
        return userDto.login();
    }
}
