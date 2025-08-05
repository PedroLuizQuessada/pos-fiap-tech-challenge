package com.example.tech_challenge.usecases.updatemenuitem;

import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.dtos.requests.UpdateMenuItemRequest;
import com.example.tech_challenge.entities.*;
import com.example.tech_challenge.exceptions.MenuItemNameAlreadyInUseException;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UpdateMenuItemByRequesterUseCaseTest {

    private MenuItemGateway menuItemGateway;
    private TokenGateway tokenGateway;
    private UpdateMenuItemByRequesterUseCase useCase;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        tokenGateway = mock(TokenGateway.class);
        useCase = new UpdateMenuItemByRequesterUseCase(menuItemGateway, tokenGateway);
    }

    @Test
    void shouldUpdateMenuItemSuccessfully() {
        // Arrange
        String token = "valid-token";
        Requester requester = new Requester("OWNER", "ownerLogin");
        Address address = new Address(1L, "SP", "São Paulo", "Rua X", "100", "12345678", null);
        UserType userType = new UserType(1L, "OWNER");
        User owner = new User(1L, "Alice", "alice@mail.com", "ownerLogin", "secure123", null, address, userType, false);
        Restaurant restaurant = new Restaurant(1L, "Pizzaria", address, "Italiana", "10:00 22:00", owner);

        MenuItem menuItem = new MenuItem(1L, restaurant, "Pizza Margherita", "Tomate e mussarela", 30.0, true, "img.jpg");

        UpdateMenuItemRequest updateRequest = new UpdateMenuItemRequest(
                "Pizzaria", "Pizza Margherita", "Pizza Quatro Queijos", "Nova descrição", 35.0, false, "img2.jpg"
        );

        MenuItem updatedMenuItem = new MenuItem(1L, restaurant, "Pizza Quatro Queijos", "Nova descrição", 35.0, false, "img2.jpg");

        when(tokenGateway.getRequester(token)).thenReturn(requester);
        when(menuItemGateway.findByRestaurantNameAndOwnerLoginAndName("Pizzaria", "ownerLogin", "Pizza Margherita"))
                .thenReturn(menuItem);
        when(menuItemGateway.countByNameAndRestaurant("Pizza Quatro Queijos", restaurant.getId())).thenReturn(0L);
        when(menuItemGateway.updateMenuItem(any(MenuItemDto.class))).thenReturn(updatedMenuItem);

        // Act
        MenuItem result = useCase.execute(updateRequest, token);

        // Assert
        assertNotNull(result);
        assertEquals("Pizza Quatro Queijos", result.getName());
        assertEquals("Nova descrição", result.getDescription());
        assertEquals(35.0, result.getPrice());
        assertFalse(result.getAvailableOnline());
        assertEquals("img2.jpg", result.getPicture());

        verify(tokenGateway).getRequester(token);
        verify(menuItemGateway).findByRestaurantNameAndOwnerLoginAndName("Pizzaria", "ownerLogin", "Pizza Margherita");
        verify(menuItemGateway).countByNameAndRestaurant("Pizza Quatro Queijos", restaurant.getId());
        verify(menuItemGateway).updateMenuItem(any(MenuItemDto.class));
    }

    @Test
    void shouldThrowExceptionWhenMenuItemNameAlreadyExists() {
        // Arrange
        String token = "token";
        Requester requester = new Requester("OWNER", "ownerLogin");
        Address address = new Address(1L, "SP", "Santos", "Av. Beach", "12", "22222222", null);
        UserType userType = new UserType(2L, "OWNER");
        User owner = new User(2L, "Bob", "bob@email.com", "ownerLogin", "password123", null, address, userType, false);
        Restaurant restaurant = new Restaurant(1L, "Bob's Burgers", address, "Fast Food", "09:00 21:00", owner);

        MenuItem menuItem = new MenuItem(1L, restaurant, "X-Burger", "Cheeseburger", 15.0, true, "burger.jpg");

        UpdateMenuItemRequest updateRequest = new UpdateMenuItemRequest(
                "Bob's Burgers", "X-Burger", "X-Bacon", "Bacon burger", 18.0, true, "bacon.jpg"
        );

        when(tokenGateway.getRequester(token)).thenReturn(requester);
        when(menuItemGateway.findByRestaurantNameAndOwnerLoginAndName("Bob's Burgers", "ownerLogin", "X-Burger"))
                .thenReturn(menuItem);
        when(menuItemGateway.countByNameAndRestaurant("X-Bacon", restaurant.getId())).thenReturn(1L); // Already in use

        // Act & Assert
        assertThrows(MenuItemNameAlreadyInUseException.class, () -> useCase.execute(updateRequest, token));

        verify(menuItemGateway, never()).updateMenuItem(any());
    }
}
