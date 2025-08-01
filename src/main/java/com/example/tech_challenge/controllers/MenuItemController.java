package com.example.tech_challenge.controllers;

import com.example.tech_challenge.datasources.MenuItemDataSource;
import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.dtos.requests.*;
import com.example.tech_challenge.dtos.responses.MenuItemResponse;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;
import com.example.tech_challenge.usecases.CreateMenuItemUseCase;
import com.example.tech_challenge.usecases.FindMenuItensByRestaurantUseCase;
import com.example.tech_challenge.usecases.deletemenuitem.DeleteMenuItemByRequesterUseCase;
import com.example.tech_challenge.usecases.deletemenuitem.DeleteMenuItemUseCase;
import com.example.tech_challenge.usecases.FindMenuItensByRestaurantNameUseCase;
import com.example.tech_challenge.usecases.updatemenuitem.UpdateMenuItemByRequesterUseCase;
import com.example.tech_challenge.usecases.updatemenuitem.UpdateMenuItemUseCase;

import java.util.List;

public class MenuItemController {

    private final MenuItemDataSource menuItemDataSource;
    private final RestaurantDataSource restaurantDataSource;
    private final TokenDataSource tokenDataSource;

    public MenuItemController(MenuItemDataSource menuItemDataSource, RestaurantDataSource restaurantDataSource, TokenDataSource tokenDataSource) {
        this.menuItemDataSource = menuItemDataSource;
        this.restaurantDataSource = restaurantDataSource;
        this.tokenDataSource = tokenDataSource;
    }

    public MenuItemResponse createMenuItem(CreateMenuItemRequest menuItemRequest, String token) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        RestaurantGateway restaurantGateway = new RestaurantGateway(restaurantDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        CreateMenuItemUseCase createMenuItemUseCase = new CreateMenuItemUseCase(menuItemGateway, restaurantGateway, tokenGateway);
        MenuItem menuItem = createMenuItemUseCase.execute(menuItemRequest, token);
        return MenuItemMapper.toResponse(menuItem);
    }

    public List<MenuItemResponse> findMenuItensByRestaurantName(int page, int size, String restaurantName) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        FindMenuItensByRestaurantNameUseCase findMenuItensByRestaurantUseCase = new FindMenuItensByRestaurantNameUseCase(menuItemGateway);
        List<MenuItem> menuItemList = findMenuItensByRestaurantUseCase.execute(page, size, restaurantName);
        return menuItemList.stream().map(MenuItemMapper::toResponse).toList();
    }

    public List<MenuItemResponse> findMenuItensByRestaurant(int page, int size, Long restaurant) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        FindMenuItensByRestaurantUseCase findMenuItensByRestaurantUseCase = new FindMenuItensByRestaurantUseCase(menuItemGateway);
        List<MenuItem> menuItemList = findMenuItensByRestaurantUseCase.execute(page, size, restaurant);
        return menuItemList.stream().map(MenuItemMapper::toAdminResponse).toList();
    }

    public MenuItemResponse updateMenuItemByRequester(UpdateMenuItemRequest updateRequest, String token) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        UpdateMenuItemByRequesterUseCase updateMenuItemByRequesterUseCase = new UpdateMenuItemByRequesterUseCase(menuItemGateway, tokenGateway);
        MenuItem menuItem = updateMenuItemByRequesterUseCase.execute(updateRequest, token);
        return MenuItemMapper.toResponse(menuItem);
    }

    public MenuItemResponse updateMenuItem(AdminUpdateMenuItemRequest updateRequest, Long id) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        UpdateMenuItemUseCase updateMenuItemUseCase = new UpdateMenuItemUseCase(menuItemGateway);
        MenuItem menuItem = updateMenuItemUseCase.execute(updateRequest, id);
        return MenuItemMapper.toResponse(menuItem);
    }

    public void deleteMenuItemByRequester(DeleteMenuItemRequest deleteRequest, String token) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        TokenGateway tokenGateway = new TokenGateway(tokenDataSource);
        DeleteMenuItemByRequesterUseCase deleteMenuItemByRequesterUseCase = new DeleteMenuItemByRequesterUseCase(menuItemGateway, tokenGateway);
        deleteMenuItemByRequesterUseCase.execute(deleteRequest, token);
    }

    public void deleteMenuItem(Long id) {
        MenuItemGateway menuItemGateway = new MenuItemGateway(menuItemDataSource);
        DeleteMenuItemUseCase deleteMenuItemUseCase = new DeleteMenuItemUseCase(menuItemGateway);
        deleteMenuItemUseCase.execute(id);
    }
}
