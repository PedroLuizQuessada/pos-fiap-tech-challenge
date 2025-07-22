package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.MenuItemDataSource;
import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.mappers.MenuItemMapper;

public class MenuItemGateway {

    private final MenuItemDataSource menuItemDataSource;

    public MenuItemGateway(MenuItemDataSource menuItemDataSource) {
        this.menuItemDataSource = menuItemDataSource;
    }

    public MenuItem createMenuItem(MenuItemDto createMenuItem) {
        MenuItemDto menuItemDto = menuItemDataSource.createMenuItem(createMenuItem);
        return MenuItemMapper.toEntity(menuItemDto);
    }

    public Long countByNameAndRestaurant(String name, Long restaurant) {
        return menuItemDataSource.countByNameAndRestaurant(name, restaurant);
    }
}
