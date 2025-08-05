package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.dtos.responses.MenuItemResponse;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.helper.MenuItemHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MenuItemMapperTest {

    @Test
    public void toEntityTest() {
        UserDto userDto = new UserDto(1L, "nome", "email@email.com", "login", "senha123",
                null, null, new UserTypeDto(1L, "tipo"));
        RestaurantDto restaurantDto = new RestaurantDto(1L, "Restaurante", null, "tipo de cozinha",
                "08:00 18:00", userDto);
        MenuItemDto menuItemDto = new MenuItemDto(1L, restaurantDto, "Nome", "Descrição", 99D,
                true, "foto");
        MenuItem menuItem = MenuItemMapper.toEntity(menuItemDto);

        assertNotNull(menuItem);
    }

    @Test
    public void toResponseTest() {
        MenuItemResponse menuItemResponse = MenuItemMapper.toResponse(MenuItemHelper.getValidMenuItem());

        assertNotNull(menuItemResponse);
    }

    @Test
    public void toAdminResponseTest() {
        MenuItemResponse menuItemResponse = MenuItemMapper.toAdminResponse(MenuItemHelper.getValidMenuItem());

        assertNotNull(menuItemResponse);
    }
}
