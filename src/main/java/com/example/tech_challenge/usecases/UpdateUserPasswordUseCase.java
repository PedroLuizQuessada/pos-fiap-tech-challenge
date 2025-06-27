package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.UpdateUserPasswordRequest;
import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.gateways.UserGateway;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

public class UpdateUserPasswordUseCase {

    private final UserGateway userGateway;
    private final TokenGateway tokenGateway;

    public UpdateUserPasswordUseCase(UserGateway userGateway, TokenGateway tokenGateway) {
        this.userGateway = userGateway;
        this.tokenGateway = tokenGateway;
    }

    public String execute(UpdateUserPasswordRequest updateUserPasswordRequest, UserDetails userDetails, String token) {
        String login = (!Objects.isNull(userDetails)) ? userDetails.getUsername() : tokenGateway.getTokenUsername(token);
        User user = userGateway.findUserByLogin(login);

        Address address = null;
        AddressDto addressDto = null;
        if (!Objects.isNull(user.getAddress())) {
            address = new Address(user.getAddress().getId(), user.getAddress().getState(), user.getAddress().getCity(),
                    user.getAddress().getStreet(), user.getAddress().getNumber(), user.getAddress().getZipCode(), user.getAddress().getAditionalInfo());

            addressDto = new AddressDto(user.getAddress().getId(), user.getAddress().getState(), user.getAddress().getCity(),
                    user.getAddress().getStreet(), user.getAddress().getNumber(), user.getAddress().getZipCode(), user.getAddress().getAditionalInfo());
        }

        user = new User(user.getId(), user.getName(), user.getEmail(), user.getLogin(), updateUserPasswordRequest.newPassword(),
                null, address, user.getAuthority(), true);

        userGateway.updateUser(new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(),
                user.getLastUpdateDate(), addressDto, user.getAuthority()));
        return user.getLogin();
    }
}
