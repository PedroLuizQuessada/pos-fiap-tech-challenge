package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.UpdateUserRequest;
import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.exceptions.EmailAlreadyInUseException;
import com.example.tech_challenge.exceptions.LoginAlreadyInUseException;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.mappers.AddressMapper;
import com.example.tech_challenge.mappers.UserMapper;

import java.util.Objects;

public class UpdateUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;

    public UpdateUserUseCase(UserGateway userGateway, AddressGateway addressGateway) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
    }

    public User execute(UpdateUserRequest updateUserRequest, String login) {
        User oldUser = userGateway.findUserByLogin(login);
        return updateUser(updateUserRequest, oldUser);
    }

    public User execute(UpdateUserRequest updateUserRequest, Long id) {
        User oldUser = userGateway.findUserById(id);
        return updateUser(updateUserRequest, oldUser);
    }

    private User updateUser(UpdateUserRequest updateUserRequest, User user) {
        Address oldAddress = user.getAddress();
        Address address = !Objects.isNull(updateUserRequest.address()) ?
                AddressMapper.toEntity(new AddressDto(!Objects.isNull(user.getAddress()) ? user.getAddress().getId() : null,
                        updateUserRequest.address().state(), updateUserRequest.address().city(), updateUserRequest.address().street(),
                        updateUserRequest.address().number(), updateUserRequest.address().zipCode(), updateUserRequest.address().aditionalInfo()))
                : null;

        //TODO sets antes de checks
        if (!Objects.equals(updateUserRequest.email(), user.getEmail())) {
            checkEmailAlreadyInUse(updateUserRequest.email());
        }
        if (!Objects.equals(updateUserRequest.login(), user.getLogin())) {
            checkLoginAlreadyInUse(updateUserRequest.login());
        }

        user.setName(updateUserRequest.name());
        user.setEmail(updateUserRequest.email());
        user.setLogin(updateUserRequest.login());
        user.setAddress(address);

        user = userGateway.updateUser(UserMapper.toDto(user));

        if (Objects.isNull(updateUserRequest.address()) && !Objects.isNull(oldAddress))
            addressGateway.delete(AddressMapper.toDto(oldAddress));

        return user;
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
