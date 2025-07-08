package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.mappers.RestaurantMapper;

import java.util.List;

public class RestaurantGateway {

    private final RestaurantDataSource restaurantDataSource;

    public RestaurantGateway(RestaurantDataSource restaurantDataSource) {
        this.restaurantDataSource = restaurantDataSource;
    }

    public Restaurant createRestaurant(RestaurantDto createRestaurantDto) {
        RestaurantDto restaurantDto = restaurantDataSource.createRestaurant(createRestaurantDto);
        return RestaurantMapper.toEntity(restaurantDto);
    }

    public Long countByName(String name) {
        return restaurantDataSource.countByName(name);
    }

    public List<Restaurant> findRestaurantsByOwner(Long ownerId) {
        List<RestaurantDto> restaurantList = restaurantDataSource.findRestaurantsByOwner(ownerId);
        return restaurantList.stream().map(RestaurantMapper::toEntity).toList();
    }
}
