package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.FindMenuItensRequest;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.gateways.MenuItemGateway;

import java.util.List;

public class FindMenuItensByRestaurantNameUseCase {

    private final MenuItemGateway menuItemGateway;

    public FindMenuItensByRestaurantNameUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public List<MenuItem> execute(int page, int size, FindMenuItensRequest request) {
        return menuItemGateway.findMenuItensByRestaurant(page, size, request.restaurantName());
    }

}
