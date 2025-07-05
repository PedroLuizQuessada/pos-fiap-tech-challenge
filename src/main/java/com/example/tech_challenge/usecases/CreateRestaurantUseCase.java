package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.exceptions.RestaurantNameAlreadyInUseException;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.UserGateway;

import java.util.Objects;

public class CreateRestaurantUseCase {

    private final UserGateway userGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateRestaurantUseCase(UserGateway userGateway, RestaurantGateway restaurantGateway) {
        this.userGateway = userGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant execute(RestaurantRequest createRestaurant, String ownerLogin) {
        User owner = userGateway.findUserByLogin(ownerLogin);
        UserDto ownerDto = getOwnerDto(owner);

        Address address = null;
        AddressDto addressDto = null;
        if (!Objects.isNull(createRestaurant.address())) {
            address = new Address(null, createRestaurant.address().state(), createRestaurant.address().city(), createRestaurant.address().street(),
                    createRestaurant.address().number(), createRestaurant.address().zipCode(), createRestaurant.address().aditionalInfo());

            addressDto = new AddressDto(null, createRestaurant.address().state(), createRestaurant.address().city(), createRestaurant.address().street(),
                    createRestaurant.address().number(), createRestaurant.address().zipCode(), createRestaurant.address().aditionalInfo());
        }

        Restaurant restaurant = new Restaurant(null, createRestaurant.name(), address, createRestaurant.kitchenType(),
                createRestaurant.openingHours(), owner);

        checkNameAlreadyInUse(restaurant.getName());

        return restaurantGateway.createRestaurant(new RestaurantDto(null, restaurant.getName(), addressDto, restaurant.getKitchenType(),
                restaurant.getOpeningHours(), ownerDto));
    }

    private UserDto getOwnerDto(User owner) {
        AddressDto ownerAddressDto = null;
        if (!Objects.isNull(owner.getAddress())) {
            ownerAddressDto = new AddressDto(owner.getAddress().getId(), owner.getAddress().getState(), owner.getAddress().getCity(),
                    owner.getAddress().getStreet(), owner.getAddress().getNumber(), owner.getAddress().getZipCode(),
                    owner.getAddress().getAditionalInfo());
        }

        UserTypeDto ownerUserTypeDto = null;
        if (!Objects.isNull(owner.getUserType())) {
            ownerUserTypeDto = new UserTypeDto(owner.getUserType().getId(), owner.getUserType().getName());
        }

        return new UserDto(owner.getId(), owner.getName(), owner.getEmail(), owner.getLogin(), owner.getPassword(), owner.getLastUpdateDate(),
                ownerAddressDto, ownerUserTypeDto);
    }

    private void checkNameAlreadyInUse(String name) {
        if (restaurantGateway.countByName(name) > 0) {
            throw new RestaurantNameAlreadyInUseException();
        }
    }
}
