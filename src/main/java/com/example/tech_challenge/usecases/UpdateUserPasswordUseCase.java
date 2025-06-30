package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.dtos.requests.UpdateUserPasswordRequest;
import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.gateways.UserGateway;

import java.util.Objects;

public class UpdateUserPasswordUseCase {

    private final UserGateway userGateway;

    public UpdateUserPasswordUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(UpdateUserPasswordRequest updateUserPasswordRequest, String login) {
        User user = userGateway.findUserByLogin(login);

        Address address = user.getAddress();
        AddressDto addressDto = null;
        if (!Objects.isNull(user.getAddress())) {
            addressDto = new AddressDto(user.getAddress().getId(), user.getAddress().getState(), user.getAddress().getCity(),
                    user.getAddress().getStreet(), user.getAddress().getNumber(), user.getAddress().getZipCode(), user.getAddress().getAditionalInfo());
        }

        UserType userType = user.getUserType();
        UserTypeDto userTypeDto = null;
        if (!Objects.isNull(user.getUserType()))
            userTypeDto = new UserTypeDto(userType.getId(), userType.getName());

        user = new User(user.getId(), user.getName(), user.getEmail(), user.getLogin(), updateUserPasswordRequest.newPassword(),
                null, address, userType, true);

        userGateway.updateUser(new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(),
                user.getLastUpdateDate(), addressDto, userTypeDto));
    }
}
