package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.dtos.requests.DeleteRestaurantRequest;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.mappers.RestaurantMapper;

import java.util.Objects;

public class DeleteRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final AddressGateway addressGateway;

    public DeleteRestaurantUseCase(RestaurantGateway restaurantGateway, AddressGateway addressGateway) {
        this.restaurantGateway = restaurantGateway;
        this.addressGateway = addressGateway;
    }

    public void execute(DeleteRestaurantRequest deleteRestaurantRequest, String ownerLogin) {
        Restaurant restaurant = restaurantGateway.findRestaurantByNameAndOwnerLogin(deleteRestaurantRequest.name(), ownerLogin);
        deleteRestaurant(RestaurantMapper.toDto(restaurant));
    }

    public void execute(Long id) {
        Restaurant restaurant = restaurantGateway.findRestaurantById(id);
        deleteRestaurant(RestaurantMapper.toDto(restaurant));
    }

    private void deleteRestaurant(RestaurantDto restaurantDto) {
        restaurantGateway.deleteRestaurant(restaurantDto);

        if (!Objects.isNull(restaurantDto.address()))
            addressGateway.delete(restaurantDto.address());
    }

}
