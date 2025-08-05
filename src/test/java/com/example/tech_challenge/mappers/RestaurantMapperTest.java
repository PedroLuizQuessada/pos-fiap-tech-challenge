package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.dtos.responses.RestaurantResponse;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.helper.RestaurantHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestaurantMapperTest {

    @Test
    public void toEntityTest() {
        UserDto userDto = new UserDto(1L, "Nome", "email@email.com", "login", "senha123", null,
                null, new UserTypeDto(1L, "nome"));
        RestaurantDto restaurantDto = new RestaurantDto(1L, "Nome", null, "Tipo de cozinha",
                "08:00 18:00", userDto);
        Restaurant restaurant = RestaurantMapper.toEntity(restaurantDto);

        assertNotNull(restaurant);
    }

    @Test
    public void toResponseTest() {
        RestaurantResponse restaurantResponse = RestaurantMapper.toResponse(RestaurantHelper.getValidRestaurant());

        assertNotNull(restaurantResponse);
    }

    @Test
    public void toAdminResponseTest() {
        RestaurantResponse restaurantResponse = RestaurantMapper.toAdminResponse(RestaurantHelper.getValidRestaurant());

        assertNotNull(restaurantResponse);
    }
}
