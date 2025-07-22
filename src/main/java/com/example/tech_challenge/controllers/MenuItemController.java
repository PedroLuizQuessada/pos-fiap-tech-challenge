package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.MenuItemDataSource;
import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.dtos.requests.MenuItemRequest;
import com.example.tech_challenge.dtos.responses.MenuItemResponse;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;
import com.example.tech_challenge.usecases.CreateMenuItemUseCase;

public class MenuItemController {

    private final MenuItemDataSource menuItemDataSource;
    private final RestaurantDataSource restaurantDataSource;

    public MenuItemController(MenuItemDataSource menuItemDataSource, RestaurantDataSource restaurantDataSource) {
        this.menuItemDataSource = menuItemDataSource;
        this.restaurantDataSource = restaurantDataSource;
    }

    public MenuItemResponse createMenuItem(MenuItemRequest menuItemRequest, String ownerLogin) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        CreateMenuItemUseCase createMenuItemUseCase = new CreateMenuItemUseCase(menuItemGateway, restaurantGateway);
        MenuItem menuItem = createMenuItemUseCase.execute(menuItemRequest, ownerLogin);
        return MenuItemMapper.toResponse(menuItem);
    }
}
