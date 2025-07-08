package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.UserGateway;

import java.util.List;

public class FindRestaurantsByOwnerUseCase {

    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public FindRestaurantsByOwnerUseCase(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    public List<Restaurant> execute(String ownerLogin) {
        User user = userGateway.findUserByLogin(ownerLogin);
        return execute(user.getId());
    }

    public List<Restaurant> execute(Long ownerId) {
        return restaurantGateway.findRestaurantsByOwner(ownerId);
    }
}
