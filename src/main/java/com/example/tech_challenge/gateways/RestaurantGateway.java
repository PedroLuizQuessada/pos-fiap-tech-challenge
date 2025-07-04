package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;

import java.util.Objects;

public class RestaurantGateway {

    private final RestaurantDataSource restaurantDataSource;

    public RestaurantGateway(RestaurantDataSource restaurantDataSource) {
        this.restaurantDataSource = restaurantDataSource;
    }

    public Restaurant createRestaurant(RestaurantDto createRestaurantDto) {
        RestaurantDto restaurantDto = restaurantDataSource.createRestaurant(createRestaurantDto);
        return createEntity(restaurantDto);
    }

    public Long countByName(String name) {
        return restaurantDataSource.countByName(name);
    }

    //TODO criar mappers
    private Restaurant createEntity(RestaurantDto restaurantDto) {
        User owner = null;
        if (!Objects.isNull(restaurantDto.owner())) {
            Address ownerAddress = null;
            if (!Objects.isNull(restaurantDto.owner().addressDto()))
                ownerAddress = new Address(restaurantDto.owner().addressDto().id(), restaurantDto.owner().addressDto().state(),
                        restaurantDto.owner().addressDto().city(), restaurantDto.owner().addressDto().street(),
                        restaurantDto.owner().addressDto().number(), restaurantDto.owner().addressDto().zipCode(),
                        restaurantDto.owner().addressDto().aditionalInfo());

            UserType ownerUserType = null;
            if (!Objects.isNull(restaurantDto.owner().userTypeDto()))
                ownerUserType = new UserType(restaurantDto.owner().userTypeDto().id(), restaurantDto.owner().userTypeDto().name());

            owner = new User(restaurantDto.owner().id(), restaurantDto.owner().name(), restaurantDto.owner().email(),
                    restaurantDto.owner().login(), restaurantDto.owner().password(), restaurantDto.owner().lastUpdateDate(),
                    ownerAddress, ownerUserType, false);
        }

        Address address = null;
        if (!Objects.isNull(restaurantDto.address()))
            address = new Address(restaurantDto.address().id(), restaurantDto.address().state(), restaurantDto.address().city(),
                    restaurantDto.address().street(), restaurantDto.address().number(), restaurantDto.address().zipCode(),
                    restaurantDto.address().aditionalInfo());

        return new Restaurant(restaurantDto.id(), restaurantDto.name(), address, restaurantDto.kitchenType(),
                restaurantDto.openingHours(), owner);
    }
}
