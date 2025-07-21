package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.requests.DeleteRestaurantRequest;
import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.dtos.requests.UpdateRestaurantRequest;
import com.example.tech_challenge.dtos.responses.RestaurantResponse;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.mappers.RestaurantMapper;
import com.example.tech_challenge.usecases.CreateRestaurantUseCase;
import com.example.tech_challenge.usecases.DeleteRestaurantUseCase;
import com.example.tech_challenge.usecases.FindRestaurantsByOwnerUseCase;
import com.example.tech_challenge.usecases.UpdateRestaurantUseCase;

import java.util.List;

public class RestaurantController {

    private final UserDataSource userDataSource;
    private final RestaurantDataSource restaurantDataSource;
    private final AddressDataSource addressDataSource;

    public RestaurantController(UserDataSource userDataSource, RestaurantDataSource restaurantDataSource, AddressDataSource addressDataSource) {
        this.userDataSource = userDataSource;
        this.restaurantDataSource = restaurantDataSource;
        this.addressDataSource = addressDataSource;
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

    public RestaurantResponse updateRestaurant(UpdateRestaurantRequest updateAddress, String ownerLogin) {
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        UpdateRestaurantUseCase updateRestaurantUseCase = new UpdateRestaurantUseCase(restaurantGateway, addressGateway);
        Restaurant restaurant = updateRestaurantUseCase.execute(updateAddress, ownerLogin);
        return RestaurantMapper.toResponse(restaurant);
    }

    public RestaurantResponse updateRestaurant(RestaurantRequest updateRestaurant, Long id) {
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        UpdateRestaurantUseCase updateRestaurantUseCase = new UpdateRestaurantUseCase(restaurantGateway, addressGateway);
        Restaurant restaurant = updateRestaurantUseCase.execute(updateRestaurant, id);
        return RestaurantMapper.toResponse(restaurant);
    }

    public void deleteRestaurant(DeleteRestaurantRequest deleteRestaurantRequest, String login) {
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        DeleteRestaurantUseCase deleteRestaurantUseCase = new DeleteRestaurantUseCase(restaurantGateway, addressGateway);
        deleteRestaurantUseCase.execute(deleteRestaurantRequest, login);
    }

    public void deleteRestaurant(Long id) {
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        DeleteRestaurantUseCase deleteRestaurantUseCase = new DeleteRestaurantUseCase(restaurantGateway, addressGateway);
        deleteRestaurantUseCase.execute(id);
    }

    private FindRestaurantsByOwnerUseCase getFindRestaurantsByOwnerUseCase() {
        UserGateway userGateway = new UserGateway(userDataSource);
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        return new FindRestaurantsByOwnerUseCase(restaurantGateway, userGateway);
    }
}
