package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.MenuItemDto;

public interface MenuItemDataSource {
    MenuItemDto createMenuItem(MenuItemDto menuItemDto);
    Long countByNameAndRestaurant(String name, Long restaurant);
}
