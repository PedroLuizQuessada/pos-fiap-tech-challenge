package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.gateways.MenuItemGateway;

import java.util.List;

public class FindMenuItensByRestaurantUseCase {

    private final MenuItemGateway menuItemGateway;

    public FindMenuItensByRestaurantUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public List<MenuItem> execute(Long restaurant) {
        return menuItemGateway.findMenuItensByRestaurant(restaurant);
    }
}
