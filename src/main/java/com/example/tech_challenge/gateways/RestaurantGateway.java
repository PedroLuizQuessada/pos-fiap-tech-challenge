package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.exceptions.RestaurantNotFoundException;
import com.example.tech_challenge.mappers.RestaurantMapper;

import java.util.List;
import java.util.Optional;

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

    public Restaurant findRestaurantByNameAndOwnerLogin(String name, String ownerLogin) {
        Optional<RestaurantDto> restaurantDtoOptional = restaurantDataSource.findRestaurantByNameAndOwnerLogin(name, ownerLogin);

        if (restaurantDtoOptional.isEmpty())
            throw new RestaurantNotFoundException();

        return RestaurantMapper.toEntity(restaurantDtoOptional.get());
    }

    public Restaurant findRestaurantById(Long id) {
        Optional<RestaurantDto> restaurantDtoOptional = restaurantDataSource.findRestaurantById(id);

        if (restaurantDtoOptional.isEmpty())
            throw new RestaurantNotFoundException();

        return RestaurantMapper.toEntity(restaurantDtoOptional.get());
    }

    public Restaurant findRestaurantByIdAndOwnerLogin(Long id, String ownerLogin) {
        Optional<RestaurantDto> restaurantDtoOptional = restaurantDataSource.findRestaurantByIdAndOwnerLogin(id, ownerLogin);

        if (restaurantDtoOptional.isEmpty())
            throw new RestaurantNotFoundException();

        return RestaurantMapper.toEntity(restaurantDtoOptional.get());
    }

    public Restaurant updateRestaurant(RestaurantDto updateRestaurantDto) {
        RestaurantDto restaurantDto = restaurantDataSource.updateRestaurant(updateRestaurantDto);
        return RestaurantMapper.toEntity(restaurantDto);
    }

    public void deleteRestaurant(RestaurantDto restaurantDto) {
        restaurantDataSource.deleteRestaurant(restaurantDto);
    }
}
