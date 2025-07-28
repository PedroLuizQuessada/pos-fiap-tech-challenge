package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.*;
import com.example.tech_challenge.dtos.requests.DeleteRestaurantRequest;
import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.dtos.requests.UpdateRestaurantRequest;
import com.example.tech_challenge.dtos.responses.RestaurantResponse;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.*;
import com.example.tech_challenge.mappers.RestaurantMapper;
import com.example.tech_challenge.usecases.CreateRestaurantUseCase;
import com.example.tech_challenge.usecases.deleterestaurant.DeleteRestaurantByRequesterUseCase;
import com.example.tech_challenge.usecases.deleterestaurant.DeleteRestaurantUseCase;
import com.example.tech_challenge.usecases.findrestaurantsbyowner.FindRestaurantsByOwnerByRequesterUseCase;
import com.example.tech_challenge.usecases.findrestaurantsbyowner.FindRestaurantsByOwnerUseCase;
import com.example.tech_challenge.usecases.updaterestaurant.UpdateRestaurantByRequesterUseCase;
import com.example.tech_challenge.usecases.updaterestaurant.UpdateRestaurantUseCase;

import java.util.List;

public class RestaurantController {

    private final UserDataSource userDataSource;
    private final RestaurantDataSource restaurantDataSource;
    private final AddressDataSource addressDataSource;
    private final MenuItemDataSource menuItemDataSource;
    private final TokenDataSource tokenDataSource;

    public RestaurantController(UserDataSource userDataSource, RestaurantDataSource restaurantDataSource,
                                AddressDataSource addressDataSource, MenuItemDataSource menuItemDataSource, TokenDataSource tokenDataSource) {
        this.userDataSource = userDataSource;
        this.restaurantDataSource = restaurantDataSource;
        this.addressDataSource = addressDataSource;
        this.menuItemDataSource = menuItemDataSource;
        this.tokenDataSource = tokenDataSource;
    }

    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest, String token) {
        UserGateway userGateway = new UserGateway(userDataSource);
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        CreateRestaurantUseCase createRestaurantUseCase = new CreateRestaurantUseCase(userGateway, restaurantGateway, tokenGateway);
        Restaurant restaurant = createRestaurantUseCase.execute(restaurantRequest, token);
        return RestaurantMapper.toResponse(restaurant);
    }

    public List<RestaurantResponse> findRestaurantsByOwnerByRequester(int page, int size, String token) {
        UserGateway userGateway = new UserGateway(userDataSource);
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        FindRestaurantsByOwnerByRequesterUseCase findRestaurantsByOwnerByRequesterUseCase =
                new FindRestaurantsByOwnerByRequesterUseCase(restaurantGateway, userGateway, tokenGateway);
        List<Restaurant> restaurantList = findRestaurantsByOwnerByRequesterUseCase.execute(page, size, token);
        return restaurantList.stream().map(RestaurantMapper::toResponse).toList();
    }

    public List<RestaurantResponse> findRestaurantsByOwner(int page, int size, Long ownerId) {
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        FindRestaurantsByOwnerUseCase findRestaurantsByOwnerUseCase = new FindRestaurantsByOwnerUseCase(restaurantGateway);
        List<Restaurant> restaurantList = findRestaurantsByOwnerUseCase.execute(page, size, ownerId);
        return restaurantList.stream().map(RestaurantMapper::toAdminResponse).toList();
    }

    public RestaurantResponse updateRestaurantByRequester(UpdateRestaurantRequest updateAddress, String token) {
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        UpdateRestaurantByRequesterUseCase updateRestaurantByRequesterUseCase =
                new UpdateRestaurantByRequesterUseCase(restaurantGateway, addressGateway, tokenGateway);
        Restaurant restaurant = updateRestaurantByRequesterUseCase.execute(updateAddress, token);
        return RestaurantMapper.toResponse(restaurant);
    }

    public RestaurantResponse updateRestaurant(RestaurantRequest updateRestaurant, Long id) {
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        UpdateRestaurantUseCase updateRestaurantUseCase = new UpdateRestaurantUseCase(restaurantGateway, addressGateway);
        Restaurant restaurant = updateRestaurantUseCase.execute(updateRestaurant, id);
        return RestaurantMapper.toResponse(restaurant);
    }

    public void deleteRestaurantByRequester(DeleteRestaurantRequest deleteRestaurantRequest, String token) {
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        DeleteRestaurantByRequesterUseCase deleteRestaurantByRequester = new DeleteRestaurantByRequesterUseCase(restaurantGateway, addressGateway, menuItemGateway, tokenGateway);
        deleteRestaurantByRequester.execute(deleteRestaurantRequest, token);
    }

    public void deleteRestaurant(Long id) {
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        AddressGateway addressGateway = new AddressGateway(addressDataSource);
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        DeleteRestaurantUseCase deleteRestaurantUseCase = new DeleteRestaurantUseCase(restaurantGateway, addressGateway, menuItemGateway);
        deleteRestaurantUseCase.execute(id);
    }
}
