package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.dtos.responses.RestaurantResponse;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.entities.User;

import java.util.Objects;

public class RestaurantMapper {

    private RestaurantMapper() {}

    public static Restaurant toEntity(RestaurantDto restaurantDto) {
        return new Restaurant(restaurantDto.id(), restaurantDto.name(),
                !Objects.isNull(restaurantDto.address()) ? AddressMapper.toEntity(restaurantDto.address()) : null,
                restaurantDto.kitchenType(), restaurantDto.openingHours(),
                !Objects.isNull(restaurantDto.owner()) ? UserMapper.toEntity(restaurantDto.owner(), false) : null);
    }

    public static Restaurant toEntity(RestaurantRequest restaurantDto, User owner) {
        return new Restaurant(null, restaurantDto.name(),
                !Objects.isNull(restaurantDto.address()) ? AddressMapper.toEntity(restaurantDto.address()) : null,
                restaurantDto.kitchenType(), restaurantDto.openingHours(), owner);
    }

    public static RestaurantDto toDto(Restaurant restaurant) {
        return new RestaurantDto(null, restaurant.getName(),
                !Objects.isNull(restaurant.getAddress()) ? AddressMapper.toDto(restaurant.getAddress()) : null, restaurant.getKitchenType(),
                restaurant.getOpeningHours(),
                !Objects.isNull(restaurant.getOwner()) ? UserMapper.toDto(restaurant.getOwner()) : null);
    }

    public static RestaurantResponse toResponse(Restaurant restaurant) {
        return new RestaurantResponse(null, restaurant.getName(),
                !Objects.isNull(restaurant.getAddress()) ? AddressMapper.toResponse(restaurant.getAddress()) : null,
                restaurant.getKitchenType(), restaurant.getOpeningHours(),
                !Objects.isNull(restaurant.getOwner()) ? UserMapper.toResponse(restaurant.getOwner()) : null);
    }

    public static RestaurantResponse toAdminResponse(Restaurant restaurant) {
        return new RestaurantResponse(restaurant.getId(), restaurant.getName(),
                !Objects.isNull(restaurant.getAddress()) ? AddressMapper.toAdminResponse(restaurant.getAddress()) : null,
                restaurant.getKitchenType(), restaurant.getOpeningHours(),
                !Objects.isNull(restaurant.getOwner()) ? UserMapper.toAdminResponse(restaurant.getOwner()) : null);
    }
}
