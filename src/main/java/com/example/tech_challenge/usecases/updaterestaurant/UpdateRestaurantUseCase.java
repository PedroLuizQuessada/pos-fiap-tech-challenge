package com.example.tech_challenge.usecases.updaterestaurant;

import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.exceptions.RestaurantNameAlreadyInUseException;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.mappers.AddressMapper;
import com.example.tech_challenge.mappers.RestaurantMapper;

import java.util.Objects;

public class UpdateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final AddressGateway addressGateway;

    public UpdateRestaurantUseCase(RestaurantGateway restaurantGateway, AddressGateway addressGateway) {
        this.restaurantGateway = restaurantGateway;
        this.addressGateway = addressGateway;
    }

    public Restaurant execute(RestaurantRequest updateRestaurant, Long id) {
        Restaurant restaurant = restaurantGateway.findRestaurantById(id);
        return updateRestaurant(updateRestaurant, restaurant);
    }

    Restaurant updateRestaurant(RestaurantRequest updateRestaurant, Restaurant restaurant) {
        Address oldAddress = restaurant.getAddress();
        Address address = !Objects.isNull(updateRestaurant.address()) ?
                AddressMapper.toEntity(updateRestaurant.address(), !Objects.isNull(restaurant.getAddress()) ? restaurant.getAddress().getId() : null)
                : null;

        String oldName = restaurant.getName();

        restaurant.setNameAndAddressAndKitchenTypeAndOpeningHours(updateRestaurant.name(), address,
                updateRestaurant.kitchenType(), updateRestaurant.openingHours());

        if (!Objects.equals(updateRestaurant.name(), oldName))
            checkNameAlreadyInUse(updateRestaurant.name());

        restaurant = restaurantGateway.updateRestaurant(RestaurantMapper.toDto(restaurant));

        if (Objects.isNull(updateRestaurant.address()) && !Objects.isNull(oldAddress))
            addressGateway.delete(AddressMapper.toDto(oldAddress));

        return restaurant;
    }

    private void checkNameAlreadyInUse(String name) {
        if (restaurantGateway.countByName(name) > 0) {
            throw new RestaurantNameAlreadyInUseException();
        }
    }
}
