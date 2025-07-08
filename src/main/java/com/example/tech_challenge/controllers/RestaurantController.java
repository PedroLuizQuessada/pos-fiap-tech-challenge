package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.dtos.responses.RestaurantResponse;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.mappers.RestaurantMapper;
import com.example.tech_challenge.usecases.CreateRestaurantUseCase;
import com.example.tech_challenge.usecases.FindRestaurantsByOwnerUseCase;

import java.util.List;

public class RestaurantController {

    private final UserDataSource userDataSource;
    private final RestaurantDataSource restaurantDataSource;

    public RestaurantController(UserDataSource userDataSource, RestaurantDataSource restaurantDataSource) {
        this.userDataSource = userDataSource;
        this.restaurantDataSource = restaurantDataSource;
    }

    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest, String login) {
        UserGateway userGateway = new UserGateway(userDataSource);
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        CreateRestaurantUseCase createRestaurantUseCase = new CreateRestaurantUseCase(userGateway, restaurantGateway);
        Restaurant restaurant = createRestaurantUseCase.execute(restaurantRequest, login);
        return RestaurantMapper.toResponse(restaurant);
    }

    public List<RestaurantResponse> findRestaurantsByOwner(String ownerLogin) {
        List<Restaurant> restaurantList = getFindRestaurantsByOwnerUseCase().execute(ownerLogin);
        return restaurantList.stream().map(RestaurantMapper::toResponse).toList();
    }

    public List<RestaurantResponse> findRestaurantsByOwner(Long ownerId) {
        List<Restaurant> restaurantList = getFindRestaurantsByOwnerUseCase().execute(ownerId);
        return restaurantList.stream().map(RestaurantMapper::toAdminResponse).toList();
    }

    private FindRestaurantsByOwnerUseCase getFindRestaurantsByOwnerUseCase() {
        UserGateway userGateway = new UserGateway(userDataSource);
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        return new FindRestaurantsByOwnerUseCase(restaurantGateway, userGateway);
    }
}
