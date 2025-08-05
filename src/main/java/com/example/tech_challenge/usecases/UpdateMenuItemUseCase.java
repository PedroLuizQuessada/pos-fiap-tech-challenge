package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.UpdateMenuItemRequest;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.exceptions.MenuItemNameAlreadyInUseException;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;

import java.util.Objects;

public class UpdateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;

    public UpdateMenuItemUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItem execute(UpdateMenuItemRequest updateRequest, String ownerLogin) {
        MenuItem menuItem = menuItemGateway.findMenuItensByRestaurantAndOwnerLoginAndName(updateRequest.restaurant(),
                ownerLogin, updateRequest.oldName());
        return update(menuItem, updateRequest);
    }

    public MenuItem execute(UpdateMenuItemRequest updateRequest, Long id) {
        MenuItem menuItem = menuItemGateway.findMenuItemById(id);
        return update(menuItem, updateRequest);
    }

    private MenuItem update(MenuItem menuItem, UpdateMenuItemRequest updateRequest) {
        String oldName = menuItem.getName();

        menuItem.setName(updateRequest.name());
        menuItem.setDescription(updateRequest.description());
        menuItem.setPrice(updateRequest.price());
        menuItem.setAvailableOnline(updateRequest.availableOnline());
        menuItem.setPicture(updateRequest.picture());

        if (!Objects.equals(oldName, menuItem.getName()))
            checkNameAlreadyInUse(menuItem.getName(), menuItem.getRestaurant().getId());

        return menuItemGateway.updateMenuItem(MenuItemMapper.toDto(menuItem));
    }

    private void checkNameAlreadyInUse(String name, Long restaurant) {
        if (menuItemGateway.countByNameAndRestaurant(name, restaurant) > 0) {
            throw new MenuItemNameAlreadyInUseException();
        }
    }
}
