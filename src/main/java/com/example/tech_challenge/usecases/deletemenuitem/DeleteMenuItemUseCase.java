package com.example.tech_challenge.usecases.deletemenuitem;

import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;

public class DeleteMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;

    public DeleteMenuItemUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public void execute(Long id) {
        MenuItem menuItem = menuItemGateway.findMenuItemById(id);
        delete(menuItem);
    }

    void delete(MenuItem menuItem) {
        menuItemGateway.deleteMenuItem(MenuItemMapper.toDto(menuItem));
    }
}
