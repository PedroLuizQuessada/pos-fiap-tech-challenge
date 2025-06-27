package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.UserDto;
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
        return deleteUser(new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(), user.getLastUpdateDate(),
                addressDto, user.getAuthority()));
    }

    public void execute(Long id) {
        User user = userGateway.findUserById(id);
        AddressDto addressDto = getAddressDto(user);
        deleteUser(new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(), user.getLastUpdateDate(),
                addressDto, user.getAuthority()));
    }

    private AddressDto getAddressDto(User user) {
        AddressDto addressDto = null;
        if (!Objects.isNull(user.getAddress()))
            addressDto = new AddressDto(user.getAddress().getId(), user.getAddress().getState(), user.getAddress().getCity(),
                    user.getAddress().getStreet(), user.getAddress().getNumber(), user.getAddress().getZipCode(), user.getAddress().getAditionalInfo());
        return addressDto;
    }

    private String deleteUser(UserDto userDto) {
        userGateway.deleteUser(userDto);
        if (!Objects.isNull(userDto.addressDto()))
            addressGateway.delete(userDto.addressDto());
        return userDto.login();
    }
}
