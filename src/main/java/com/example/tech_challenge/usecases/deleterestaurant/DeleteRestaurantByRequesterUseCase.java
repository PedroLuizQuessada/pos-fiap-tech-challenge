package com.example.tech_challenge.usecases.deleterestaurant;

import com.example.tech_challenge.dtos.requests.DeleteRestaurantRequest;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.mappers.RestaurantMapper;

public class DeleteRestaurantByRequesterUseCase {

    private final RestaurantGateway restaurantGateway;
    private final TokenGateway tokenGateway;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;

    public DeleteRestaurantByRequesterUseCase(RestaurantGateway restaurantGateway, AddressGateway addressGateway,
                                              MenuItemGateway menuItemGateway, TokenGateway tokenGateway) {
        this.restaurantGateway = restaurantGateway;
        this.tokenGateway = tokenGateway;
        this.deleteRestaurantUseCase = new DeleteRestaurantUseCase(restaurantGateway, addressGateway, menuItemGateway);
    }

    public void execute(DeleteRestaurantRequest deleteRestaurantRequest, String token) {
        Requester requester = tokenGateway.getRequester(token);
        Restaurant restaurant = restaurantGateway.findRestaurantByNameAndOwnerLogin(deleteRestaurantRequest.name(), requester.getLogin());
        deleteRestaurantUseCase.deleteRestaurant(RestaurantMapper.toDto(restaurant));
    }

}
