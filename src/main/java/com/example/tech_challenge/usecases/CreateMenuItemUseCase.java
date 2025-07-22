package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.MenuItemRequest;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.exceptions.MenuItemNameAlreadyInUseException;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;

public class CreateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateMenuItemUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public MenuItem execute(MenuItemRequest createMenuItem, String ownerLogin) {
        Restaurant restaurant = restaurantGateway.findRestaurantByIdAndOwnerLogin(createMenuItem.restaurant(), ownerLogin);
        MenuItem menuItem = MenuItemMapper.toEntity(createMenuItem, restaurant);
        checkNameAlreadyInUse(menuItem.getName(), restaurant.getId());
        return menuItemGateway.createMenuItem(MenuItemMapper.toDto(menuItem));
    }

    private void checkNameAlreadyInUse(String name, Long restaurant) {
        if (menuItemGateway.countByNameAndRestaurant(name, restaurant) > 0) {
            throw new MenuItemNameAlreadyInUseException();
        }
    }
}
