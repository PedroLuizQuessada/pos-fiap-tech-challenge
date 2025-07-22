package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.MenuItemDataSource;
import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.dtos.requests.CreateMenuItemRequest;
import com.example.tech_challenge.dtos.requests.UpdateMenuItemRequest;
import com.example.tech_challenge.dtos.responses.MenuItemResponse;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;
import com.example.tech_challenge.usecases.CreateMenuItemUseCase;
import com.example.tech_challenge.usecases.FindMenuItensByRestaurantUseCase;
import com.example.tech_challenge.usecases.UpdateMenuItemUseCase;

import java.util.List;

public class MenuItemController {

    private final MenuItemDataSource menuItemDataSource;
    private final RestaurantDataSource restaurantDataSource;

    public MenuItemController(MenuItemDataSource menuItemDataSource, RestaurantDataSource restaurantDataSource) {
        this.menuItemDataSource = menuItemDataSource;
        this.restaurantDataSource = restaurantDataSource;
    }

    public MenuItemResponse createMenuItem(CreateMenuItemRequest menuItemRequest, String ownerLogin) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        CreateMenuItemUseCase createMenuItemUseCase = new CreateMenuItemUseCase(menuItemGateway, restaurantGateway);
        MenuItem menuItem = createMenuItemUseCase.execute(menuItemRequest, ownerLogin);
        return MenuItemMapper.toResponse(menuItem);
    }

    public List<MenuItemResponse> findMenuItem(Long restaurant, String ownerLogin) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        FindMenuItensByRestaurantUseCase findMenuItensByRestaurantUseCase = new FindMenuItensByRestaurantUseCase(menuItemGateway);
        List<MenuItem> menuItemList = findMenuItensByRestaurantUseCase.execute(restaurant, ownerLogin);
        return menuItemList.stream().map(MenuItemMapper::toResponse).toList();
    }

    public List<MenuItemResponse> findMenuItem(Long restaurant) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        FindMenuItensByRestaurantUseCase findMenuItensByRestaurantUseCase = new FindMenuItensByRestaurantUseCase(menuItemGateway);
        List<MenuItem> menuItemList = findMenuItensByRestaurantUseCase.execute(restaurant);
        return menuItemList.stream().map(MenuItemMapper::toAdminResponse).toList();
    }

    public MenuItemResponse updateMenuItem(UpdateMenuItemRequest updateRequest, String ownerLogin) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        UpdateMenuItemUseCase updateMenuItemUseCase = new UpdateMenuItemUseCase(menuItemGateway);
        MenuItem menuItem = updateMenuItemUseCase.execute(updateRequest, ownerLogin);
        return MenuItemMapper.toResponse(menuItem);
    }

    public MenuItemResponse updateMenuItem(UpdateMenuItemRequest updateRequest, Long id) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        UpdateMenuItemUseCase updateMenuItemUseCase = new UpdateMenuItemUseCase(menuItemGateway);
        MenuItem menuItem = updateMenuItemUseCase.execute(updateRequest, id);
        return MenuItemMapper.toResponse(menuItem);
    }
}
