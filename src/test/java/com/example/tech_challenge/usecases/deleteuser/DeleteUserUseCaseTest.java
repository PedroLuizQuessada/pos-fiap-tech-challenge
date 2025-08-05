package com.example.tech_challenge.usecases.deleteuser;

import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.*;
import com.example.tech_challenge.enums.UserTypeEnum;
import com.example.tech_challenge.gateways.*;
import com.example.tech_challenge.mappers.MenuItemMapper;
import com.example.tech_challenge.mappers.RestaurantMapper;
import com.example.tech_challenge.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;

class DeleteUserUseCaseTest {

    private UserGateway userGateway;
    private AddressGateway addressGateway;
    private RestaurantGateway restaurantGateway;
    private MenuItemGateway menuItemGateway;
    private DeleteUserUseCase deleteUserUseCase;

    @BeforeEach
    void setup() {
        userGateway = mock(UserGateway.class);
        addressGateway = mock(AddressGateway.class);
        restaurantGateway = mock(RestaurantGateway.class);
        menuItemGateway = mock(MenuItemGateway.class);
        deleteUserUseCase = new DeleteUserUseCase(userGateway, addressGateway, restaurantGateway, menuItemGateway);
    }

    @Test
    void shouldDeleteUserWithRestaurantsMenuItemsAndAddress() {
        // Arrange
        Long userId = 1L;

        Address address = new Address(1L, "SP", "São Paulo", "Rua B", "123", "01000-000", "Fundos");

        UserType userType = new UserType(UserTypeEnum.OWNER.getId(), UserTypeEnum.OWNER.name());
        User user = new User(userId, "Lucas", "lucas@email.com", "lucas", "abcdef", new Date(Date.from(Instant.now()).getTime()), address, userType, false);

        Restaurant restaurant1 = new Restaurant(10L, "Restaurante A", address, "Brasileira", "08:00 18:00", user);
        Restaurant restaurant2 = new Restaurant(11L, "Restaurante B", null, "Mexicana", "08:00 18:00", user);

        MenuItem menuItem1 = mock(MenuItem.class);

        when(userGateway.findUserById(userId)).thenReturn(user);

        // Page 0 of restaurants
        when(restaurantGateway.findRestaurantsByOwner(0, 100, userId)).thenReturn(List.of(restaurant1, restaurant2));
        // Page 1 is empty
        when(restaurantGateway.findRestaurantsByOwner(1, 100, userId)).thenReturn(List.of());

        // Menu items for restaurant1
        when(menuItemGateway.findMenuItensByRestaurant(0, 100, restaurant1.getId())).thenReturn(List.of(menuItem1));
        when(menuItemGateway.findMenuItensByRestaurant(1, 100, restaurant1.getId())).thenReturn(List.of());

        // No menu items for restaurant2
        when(menuItemGateway.findMenuItensByRestaurant(0, 100, restaurant2.getId())).thenReturn(List.of());

        // Act
        deleteUserUseCase.execute(userId);

        // Assert
        verify(userGateway).findUserById(userId);

        verify(menuItemGateway).deleteMenuItem(MenuItemMapper.toDto(menuItem1));

        verify(restaurantGateway).deleteRestaurant(RestaurantMapper.toDto(restaurant1));
        verify(restaurantGateway).deleteRestaurant(RestaurantMapper.toDto(restaurant2));
    }

    @Test
    void shouldDeleteUserWithoutRestaurants() {
        // Arrange
        Long userId = 2L;

        UserType userType = new UserType(UserTypeEnum.OWNER.getId(), UserTypeEnum.OWNER.name());
        User user = new User(userId, "Ana", "ana@email.com", "ana", "abcdef", null, null, userType, false);
        UserDto userDto = UserMapper.toDto(user);

        when(userGateway.findUserById(userId)).thenReturn(user);
        when(restaurantGateway.findRestaurantsByOwner(0, 100, userId)).thenReturn(List.of());

        // Act
        deleteUserUseCase.execute(userId);

        // Assert
        verify(restaurantGateway, never()).deleteRestaurant(any());
        verify(menuItemGateway, never()).deleteMenuItem(any());
        verify(addressGateway, never()).delete(any(AddressDto.class));

        verify(userGateway).deleteUser(userDto);
    }

    @Test
    void shouldNotDeleteAddressIfUserAddressIsNull() {
        // Arrange
        Long userId = 3L;

        UserType userType = new UserType(UserTypeEnum.OWNER.getId(), UserTypeEnum.OWNER.name());
        User user = new User(userId, "Carlos", "carlos@email.com", "carlos", "abcdef", new Date(Date.from(Instant.now()).getTime()), null, userType, false);
        UserDto userDto = UserMapper.toDto(user);

        when(userGateway.findUserById(userId)).thenReturn(user);
        when(restaurantGateway.findRestaurantsByOwner(anyInt(), anyInt(), eq(userId))).thenReturn(List.of());

        // Act
        deleteUserUseCase.execute(userId);

        // Assert
        verify(addressGateway, never()).delete(any());
        verify(userGateway).deleteUser(userDto);
    }
}
