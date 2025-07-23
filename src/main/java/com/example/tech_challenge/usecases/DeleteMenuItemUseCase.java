package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.DeleteMenuItemRequest;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;

public class DeleteMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;

    public DeleteMenuItemUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public void execute(DeleteMenuItemRequest request, String ownerLogin) {
        MenuItem menuItem = menuItemGateway.findMenuItensByRestaurantAndOwnerLoginAndName(request.restaurant(), ownerLogin, request.name());
        delete(menuItem);
    }

    public void execute(Long id) {
        MenuItem menuItem = menuItemGateway.findMenuItemById(id);
        delete(menuItem);
    }

    private void delete(MenuItem menuItem) {
        menuItemGateway.deleteMenuItem(MenuItemMapper.toDto(menuItem));
    }
}
