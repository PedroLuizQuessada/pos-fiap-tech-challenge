package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.dtos.requests.MenuItemRequest;
import com.example.tech_challenge.dtos.responses.MenuItemResponse;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.entities.Restaurant;

import java.util.Objects;

public class MenuItemMapper {

    private MenuItemMapper() {}

    public static MenuItem toEntity(MenuItemRequest menuItemRequest, Restaurant restaurant) {
        return new MenuItem(null, restaurant, menuItemRequest.name(),
                menuItemRequest.description(), menuItemRequest.price(), menuItemRequest.availableOnline(), menuItemRequest.picture());
    }

    public static MenuItem toEntity(MenuItemDto menuItemDto) {
        return new MenuItem(menuItemDto.id(), !Objects.isNull(menuItemDto.restaurant()) ? RestaurantMapper.toEntity(menuItemDto.restaurant()) : null,
                menuItemDto.name(), menuItemDto.description(), menuItemDto.price(), menuItemDto.availableOnline(), menuItemDto.picture());
    }

    public static MenuItemDto toDto(MenuItem menuItem) {
        return new MenuItemDto(menuItem.getId(), !Objects.isNull(menuItem.getRestaurant()) ? RestaurantMapper.toDto(menuItem.getRestaurant()) : null,
                menuItem.getName(), menuItem.getDescription(), menuItem.getPrice(), menuItem.getAvailableOnline(), menuItem.getPicture());
    }

    public static MenuItemResponse toResponse(MenuItem menuItem) {
        return new MenuItemResponse(null, !Objects.isNull(menuItem.getRestaurant()) ? RestaurantMapper.toResponse(menuItem.getRestaurant()) : null,
                menuItem.getName(), menuItem.getDescription(), menuItem.getPrice(), menuItem.getAvailableOnline(), menuItem.getPicture());
    }

    public static MenuItemResponse toAdminResponse(MenuItem menuItem) {
        return new MenuItemResponse(null, !Objects.isNull(menuItem.getRestaurant()) ? RestaurantMapper.toAdminResponse(menuItem.getRestaurant()) : null,
                menuItem.getName(), menuItem.getDescription(), menuItem.getPrice(), menuItem.getAvailableOnline(), menuItem.getPicture());
    }
}
