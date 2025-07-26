package com.example.tech_challenge.usecases.updaterestaurant;

import com.example.tech_challenge.dtos.requests.UpdateRestaurantRequest;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.mappers.RestaurantMapper;

public class UpdateRestaurantByRequesterUseCase {

    private final RestaurantGateway restaurantGateway;
    private final TokenGateway tokenGateway;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;

    public UpdateRestaurantByRequesterUseCase(RestaurantGateway restaurantGateway, AddressGateway addressGateway, TokenGateway tokenGateway) {
        this.restaurantGateway = restaurantGateway;
        this.tokenGateway = tokenGateway;
        this.updateRestaurantUseCase = new UpdateRestaurantUseCase(restaurantGateway, addressGateway);
    }

    public Restaurant execute(UpdateRestaurantRequest updateAddress, String token) {
        Requester requester = tokenGateway.getRequester(token);
        Restaurant restaurant = restaurantGateway.findRestaurantByNameAndOwnerLogin(updateAddress.oldName(), requester.getLogin());
        return updateRestaurantUseCase.updateRestaurant(RestaurantMapper.toRequest(updateAddress), restaurant);
    }

}
