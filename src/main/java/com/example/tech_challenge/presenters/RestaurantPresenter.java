package com.example.tech_challenge.presenters;

import com.example.tech_challenge.dtos.responses.RestaurantResponse;
import com.example.tech_challenge.entities.Restaurant;

import java.util.Objects;

public class RestaurantPresenter {

    private RestaurantPresenter(){}

    public static RestaurantResponse toResponse(Restaurant restaurant) {
        return new RestaurantResponse(null, restaurant.getName(),
                !Objects.isNull(restaurant.getAddress()) ? AddressPresenter.toResponse(restaurant.getAddress()) : null,
                restaurant.getKitchenType(), restaurant.getOpeningHours(),
                !Objects.isNull(restaurant.getOwner()) ? UserPresenter.toResponse(restaurant.getOwner()) : null);
    }
}
