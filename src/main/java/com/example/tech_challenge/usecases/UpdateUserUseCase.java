package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.request.UpdateUserRequest;
import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.exception.EmailAlreadyInUseException;
import com.example.tech_challenge.exception.LoginAlreadyInUseException;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.UserGateway;

import java.util.Objects;

public class UpdateUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;

    public UpdateUserUseCase(UserGateway userGateway, AddressGateway addressGateway) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
    }

    public User execute(UpdateUserRequest updateUserRequest, Long id) {
        User oldUser = userGateway.findUserById(id);

        Address address = null;
        AddressDto addressDto = null;
        if (!Objects.isNull(updateUserRequest.address())) {
            address = new Address(oldUser.getAddress().getId(), updateUserRequest.address().state(), updateUserRequest.address().city(),
                    updateUserRequest.address().street(), updateUserRequest.address().number(), updateUserRequest.address().zipCode(),
                    updateUserRequest.address().aditionalInfo());

            addressDto = new AddressDto(oldUser.getAddress().getId(), updateUserRequest.address().state(), updateUserRequest.address().city(),
                    updateUserRequest.address().street(), updateUserRequest.address().number(), updateUserRequest.address().zipCode(),
                    updateUserRequest.address().aditionalInfo());
        }

        User user = new User(oldUser.getId(), updateUserRequest.name(), updateUserRequest.email(), updateUserRequest.login(),
                oldUser.getPassword(), null, address, oldUser.getAuthority(), false);

        if (!Objects.equals(updateUserRequest.email(), oldUser.getEmail())) {
            checkEmailAlreadyInUse(updateUserRequest.email());
        }
        if (!Objects.equals(updateUserRequest.login(), oldUser.getLogin())) {
            checkLoginAlreadyInUse(updateUserRequest.login());
        }

        if (Objects.isNull(updateUserRequest.address()) && !Objects.isNull(oldUser.getAddress()))
            addressGateway.delete(new AddressDto(oldUser.getAddress().getId(), oldUser.getAddress().getState(), oldUser.getAddress().getCity(),
                    oldUser.getAddress().getStreet(), oldUser.getAddress().getNumber(), oldUser.getAddress().getZipCode(), oldUser.getAddress().getAditionalInfo()));

        return userGateway.updateUser(new UserDto(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(),
                user.getLastUpdateDate(), addressDto, user.getAuthority()));
    }

    private void checkEmailAlreadyInUse(String email) {
        if (userGateway.countByEmail(email) > 0) {
            throw new EmailAlreadyInUseException();
        }
    }

    private void checkLoginAlreadyInUse(String login) {
        if (userGateway.countByLogin(login) > 0) {
            throw new LoginAlreadyInUseException();
        }
    }
}
