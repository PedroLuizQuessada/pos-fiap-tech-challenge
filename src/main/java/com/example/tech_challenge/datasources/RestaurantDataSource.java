package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.RestaurantDto;

public interface RestaurantDataSource {
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);
    Long countByName(String name);
}
