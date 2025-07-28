package com.example.tech_challenge.usecases.findrestaurantsbyowner;

import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.gateways.UserGateway;

import java.util.List;

public class FindRestaurantsByOwnerByRequesterUseCase {

    private final UserGateway userGateway;
    private final TokenGateway tokenGateway;
    private final FindRestaurantsByOwnerUseCase findRestaurantsByOwnerUseCase;

    public FindRestaurantsByOwnerByRequesterUseCase(RestaurantGateway restaurantGateway, UserGateway userGateway, TokenGateway tokenGateway) {
        this.userGateway = userGateway;
        this.tokenGateway = tokenGateway;
        this.findRestaurantsByOwnerUseCase = new FindRestaurantsByOwnerUseCase(restaurantGateway);
    }

    public List<Restaurant> execute(int page, int size, String token) {
        Requester requester = tokenGateway.getRequester(token);
        User user = userGateway.findUserByLogin(requester.getLogin());
        return findRestaurantsByOwnerUseCase.execute(page, size, user.getId());
    }

}
