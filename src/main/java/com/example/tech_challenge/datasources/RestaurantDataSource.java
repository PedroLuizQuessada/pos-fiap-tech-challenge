package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.RestaurantDto;

import java.util.List;
import java.util.Optional;

public interface RestaurantDataSource {
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);
    Long countByName(String name);
    List<RestaurantDto> findRestaurantsByOwner(Long ownerId);
    Optional<RestaurantDto> findRestaurantByNameAndOwnerLogin(String name, String ownerLogin);
    Optional<RestaurantDto> findRestaurantById(Long id);
    RestaurantDto updateRestaurant(RestaurantDto restaurantDto);
    void deleteRestaurant(RestaurantDto restaurantDto);
}
