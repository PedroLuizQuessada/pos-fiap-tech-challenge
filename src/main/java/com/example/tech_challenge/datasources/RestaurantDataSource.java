package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.RestaurantDto;

import java.util.List;

public interface RestaurantDataSource {
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);
    Long countByName(String name);
    List<RestaurantDto> findRestaurantsByOwner(Long ownerId);
}
