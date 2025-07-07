package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.exceptions.RestaurantNameAlreadyInUseException;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.mappers.RestaurantMapper;

public class CreateRestaurantUseCase {

    private final UserGateway userGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateRestaurantUseCase(UserGateway userGateway, RestaurantGateway restaurantGateway) {
        this.userGateway = userGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant execute(RestaurantRequest createRestaurant, String ownerLogin) {
        User owner = userGateway.findUserByLogin(ownerLogin);
        Restaurant restaurant = RestaurantMapper.toEntity(createRestaurant, owner);

        checkNameAlreadyInUse(restaurant.getName());

        return restaurantGateway.createRestaurant(RestaurantMapper.toDto(restaurant));
    }

    private void checkNameAlreadyInUse(String name) {
        if (restaurantGateway.countByName(name) > 0) {
            throw new RestaurantNameAlreadyInUseException();
        }
    }
}
