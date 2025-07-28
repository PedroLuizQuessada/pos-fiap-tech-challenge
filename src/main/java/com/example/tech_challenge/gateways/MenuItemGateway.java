package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.MenuItemDataSource;
import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.exceptions.MenuItemNotFoundException;
import com.example.tech_challenge.mappers.MenuItemMapper;

import java.util.List;
import java.util.Optional;

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

    public List<MenuItem> findMenuItensByRestaurantAndOwnerLogin(int page, int size, Long restaurant, String ownerLogin) {
        List<MenuItemDto> menuItemDtoList = menuItemDataSource.findByRestaurantAndOwnerLogin(page, size, restaurant, ownerLogin);
        return menuItemDtoList.stream().map(MenuItemMapper::toEntity).toList();
    }

    public List<MenuItem> findMenuItensByRestaurant(int page, int size, Long restaurant) {
        List<MenuItemDto> menuItemDtoList = menuItemDataSource.findByRestaurant(page, size, restaurant);
        return menuItemDtoList.stream().map(MenuItemMapper::toEntity).toList();
    }

    public MenuItem findByRestaurantNameAndOwnerLoginAndName(String restaurantName, String ownerLogin, String name) {
        Optional<MenuItemDto> optionalMenuItemDto = menuItemDataSource.findByRestaurantNameAndOwnerLoginAndName(restaurantName, ownerLogin, name);

        if (optionalMenuItemDto.isEmpty())
            throw new MenuItemNotFoundException();

        return MenuItemMapper.toEntity(optionalMenuItemDto.get());
    }

    public MenuItem findMenuItemById(Long id) {
        Optional<MenuItemDto> optionalMenuItemDto = menuItemDataSource.findById(id);

        if (optionalMenuItemDto.isEmpty())
            throw new MenuItemNotFoundException();

        return MenuItemMapper.toEntity(optionalMenuItemDto.get());
    }

    public MenuItem updateMenuItem(MenuItemDto updateMenuItemDto) {
        MenuItemDto menuItemDto = menuItemDataSource.updateMenuItem(updateMenuItemDto);
        return MenuItemMapper.toEntity(menuItemDto);
    }

    public void deleteMenuItem(MenuItemDto menuItemDto) {
        menuItemDataSource.deleteMenuItem(menuItemDto);
    }
}
