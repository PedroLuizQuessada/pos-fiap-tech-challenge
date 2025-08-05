package com.example.tech_challenge.usecases.updatemenuitem;

import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.dtos.requests.AdminUpdateMenuItemRequest;
import com.example.tech_challenge.entities.*;
import com.example.tech_challenge.exceptions.MenuItemNameAlreadyInUseException;
import com.example.tech_challenge.gateways.MenuItemGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UpdateMenuItemUseCaseTest {

    private MenuItemGateway menuItemGateway;
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        updateMenuItemUseCase = new UpdateMenuItemUseCase(menuItemGateway);
    }

    @Test
    void shouldUpdateMenuItemSuccessfully() {
        // Arrange
        Restaurant restaurant = createRestaurant();
        MenuItem menuItem = new MenuItem(1L, restaurant, "Original Name", "Old desc", 10.0, true, "img.jpg");

        AdminUpdateMenuItemRequest request = new AdminUpdateMenuItemRequest(
                "Updated Name", "New Description", 12.0, false, "newImg.jpg"
        );

        MenuItem updatedMenuItem = new MenuItem(1L, restaurant, "Updated Name", "New Description", 12.0, false, "newImg.jpg");

        when(menuItemGateway.findMenuItemById(1L)).thenReturn(menuItem);
        when(menuItemGateway.countByNameAndRestaurant("Updated Name", restaurant.getId())).thenReturn(0L);
        when(menuItemGateway.updateMenuItem(any(MenuItemDto.class))).thenReturn(updatedMenuItem);

        // Act
        MenuItem result = updateMenuItemUseCase.execute(request, 1L);

        // Assert
        assertEquals("Updated Name", result.getName());
        assertEquals("New Description", result.getDescription());
        assertEquals(12.0, result.getPrice());
        assertFalse(result.getAvailableOnline());
        assertEquals("newImg.jpg", result.getPicture());

        verify(menuItemGateway).findMenuItemById(1L);
        verify(menuItemGateway).countByNameAndRestaurant("Updated Name", restaurant.getId());
        verify(menuItemGateway).updateMenuItem(any(MenuItemDto.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExistsInSameRestaurant() {
        // Arrange
        Restaurant restaurant = createRestaurant();
        MenuItem menuItem = new MenuItem(1L, restaurant, "Original Name", "Old desc", 10.0, true, "img.jpg");

        AdminUpdateMenuItemRequest request = new AdminUpdateMenuItemRequest(
                "Updated Name", "New Description", 12.0, false, "newImg.jpg"
        );

        when(menuItemGateway.findMenuItemById(1L)).thenReturn(menuItem);
        when(menuItemGateway.countByNameAndRestaurant("Updated Name", restaurant.getId())).thenReturn(1L);

        // Act & Assert
        assertThrows(MenuItemNameAlreadyInUseException.class, () -> updateMenuItemUseCase.execute(request, 1L));

        verify(menuItemGateway).findMenuItemById(1L);
        verify(menuItemGateway).countByNameAndRestaurant("Updated Name", restaurant.getId());
        verify(menuItemGateway, never()).updateMenuItem(any());
    }

    private Restaurant createRestaurant() {
        Address address = new Address(1L, "SP", "São Paulo", "Rua A", "123", "01234567", null);
        UserType userType = new UserType(1L, "OWNER");
        User user = new User(1L, "João", "joao@email.com", "joao123", "senha123", null, address, userType, false);
        return new Restaurant(1L, "Restaurante do João", address, "Italiana", "08:00 18:00", user);
    }
}
