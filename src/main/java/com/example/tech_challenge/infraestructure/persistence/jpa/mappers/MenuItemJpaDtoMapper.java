package com.example.tech_challenge.infraestructure.persistence.jpa.mappers;

import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.MenuItemJpa;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
@Profile("jpa")
public class MenuItemJpaDtoMapper {

    private final RestaurantJpaDtoMapper restaurantJpaDtoMapper;

    public MenuItemJpa toMenuItemJpa(MenuItemDto menuItemDto) {
        return new MenuItemJpa(menuItemDto.id(),
                !Objects.isNull(menuItemDto.restaurant()) ? restaurantJpaDtoMapper.toRestaurantJpa(menuItemDto.restaurant()) : null,
                menuItemDto.name(), menuItemDto.description(), menuItemDto.price(), menuItemDto.availableOnline(), menuItemDto.picture());
    }

    public MenuItemDto toMenuItemDto(MenuItemJpa menuItemJpa) {
        return new MenuItemDto(menuItemJpa.getId(),
                !Objects.isNull(menuItemJpa.getRestaurantJpa()) ? restaurantJpaDtoMapper.toRestaurantDto(menuItemJpa.getRestaurantJpa()) : null,
                menuItemJpa.getName(), menuItemJpa.getDescription(), menuItemJpa.getPrice(), menuItemJpa.getAvailableOnline(), menuItemJpa.getPicture());
    }
}
