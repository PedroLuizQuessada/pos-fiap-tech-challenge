package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.CreateMenuItemRequest;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.exceptions.MenuItemNameAlreadyInUseException;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;

public class CreateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;
    private final TokenGateway tokenGateway;

    public CreateMenuItemUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway, TokenGateway tokenGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
        this.tokenGateway = tokenGateway;
    }

    public MenuItem execute(CreateMenuItemRequest createMenuItem, String token) {
        Requester requester = tokenGateway.getRequester(token);
        Restaurant restaurant = restaurantGateway.findRestaurantByNameAndOwnerLogin(createMenuItem.restaurantName(), requester.getLogin());
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
