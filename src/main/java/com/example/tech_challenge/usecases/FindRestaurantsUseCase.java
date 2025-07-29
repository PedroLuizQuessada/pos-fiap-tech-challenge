package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.RestaurantGateway;

import java.util.List;

public class FindRestaurantsUseCase {

    private final RestaurantGateway restaurantGateway;


    public FindRestaurantsUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public List<Restaurant> execute(int page, int size) {
        return restaurantGateway.findRestaurants(page, size);
    }
}
