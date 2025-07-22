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

    public List<MenuItem> findMenuItensByRestaurantAndOwnerLogin(Long restaurant, String ownerLogin) {
        List<MenuItemDto> menuItemDtoList = menuItemDataSource.findByRestaurantAndOwnerLogin(restaurant, ownerLogin);
        return menuItemDtoList.stream().map(MenuItemMapper::toEntity).toList();
    }

    public List<MenuItem> findMenuItensByRestaurant(Long restaurant) {
        List<MenuItemDto> menuItemDtoList = menuItemDataSource.findByRestaurant(restaurant);
        return menuItemDtoList.stream().map(MenuItemMapper::toEntity).toList();
    }

    public MenuItem findMenuItensByRestaurantAndOwnerLoginAndName(Long restaurant, String ownerLogin, String name) {
        Optional<MenuItemDto> optionalMenuItemDto = menuItemDataSource.findByRestaurantAndOwnerLoginAndName(restaurant, ownerLogin, name);

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
}
