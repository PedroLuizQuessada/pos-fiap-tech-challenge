package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.MenuItemDto;

import java.util.List;

public interface MenuItemDataSource {
    MenuItemDto createMenuItem(MenuItemDto menuItemDto);
    Long countByNameAndRestaurant(String name, Long restaurant);
    List<MenuItemDto> findByRestaurantAndOwnerLogin(Long restaurant, String ownerLogin);
    List<MenuItemDto> findByRestaurant(Long restaurant);
}
