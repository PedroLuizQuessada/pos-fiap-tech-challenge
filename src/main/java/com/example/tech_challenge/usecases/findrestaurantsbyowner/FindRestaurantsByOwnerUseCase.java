package com.example.tech_challenge.usecases.findrestaurantsbyowner;

import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.RestaurantGateway;

import java.util.List;

public class FindRestaurantsByOwnerUseCase {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantsByOwnerUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public List<Restaurant> execute(int page, int size, Long ownerId) {
        return restaurantGateway.findRestaurantsByOwner(page, size, ownerId);
    }
}
