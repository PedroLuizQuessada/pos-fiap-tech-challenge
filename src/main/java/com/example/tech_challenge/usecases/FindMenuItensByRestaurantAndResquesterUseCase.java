package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.TokenGateway;

import java.util.List;

public class FindMenuItensByRestaurantAndResquesterUseCase {

    private final MenuItemGateway menuItemGateway;
    private final TokenGateway tokenGateway;

    public FindMenuItensByRestaurantAndResquesterUseCase(MenuItemGateway menuItemGateway, TokenGateway tokenGateway) {
        this.menuItemGateway = menuItemGateway;
        this.tokenGateway = tokenGateway;
    }

    public List<MenuItem> execute(int page, int size, Long restaurant, String token) {
        Requester requester = tokenGateway.getRequester(token);
        return menuItemGateway.findMenuItensByRestaurantAndOwnerLogin(page, size, restaurant, requester.getLogin());
    }

}
