package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.MenuItemDto;

import java.util.List;
import java.util.Optional;

public interface MenuItemDataSource {
    MenuItemDto createMenuItem(MenuItemDto menuItemDto);
    Long countByNameAndRestaurant(String name, Long restaurant);
    List<MenuItemDto> findByRestaurantAndOwnerLogin(int page, int size, Long restaurant, String ownerLogin);
    List<MenuItemDto> findByRestaurant(int page, int size, Long restaurant);
    Optional<MenuItemDto> findByRestaurantNameAndOwnerLoginAndName(String restaurantName, String ownerLogin, String name);
    Optional<MenuItemDto> findById(Long id);
    MenuItemDto updateMenuItem(MenuItemDto menuItemDto);
    void deleteMenuItem(MenuItemDto menuItemDto);
}
