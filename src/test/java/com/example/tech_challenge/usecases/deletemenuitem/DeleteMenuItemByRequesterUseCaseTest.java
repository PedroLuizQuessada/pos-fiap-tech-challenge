package com.example.tech_challenge.usecases.deletemenuitem;

import com.example.tech_challenge.dtos.requests.DeleteMenuItemRequest;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.exceptions.MenuItemNotFoundException;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeleteMenuItemByRequesterUseCaseTest {

    private MenuItemGateway menuItemGateway;
    private TokenGateway tokenGateway;
    private DeleteMenuItemByRequesterUseCase deleteMenuItemByRequesterUseCase;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        tokenGateway = mock(TokenGateway.class);
//        DeleteMenuItemUseCase deleteMenuItemUseCase = mock(DeleteMenuItemUseCase.class);

        deleteMenuItemByRequesterUseCase = new DeleteMenuItemByRequesterUseCase(menuItemGateway, tokenGateway);
    }

    @Test
    void shouldDeleteMenuItemSuccessfully_whenRequesterAndMenuItemMatch() {
        // Arrange
        String token = "mocked-token";
        String restaurantName = "Pizza Palace";
        String itemName = "Margherita";

        DeleteMenuItemRequest request = new DeleteMenuItemRequest(restaurantName, itemName);
        Requester requester = new Requester("OWNER", "owner_login");
        MenuItem menuItem = mock(MenuItem.class);

        when(tokenGateway.getRequester(token)).thenReturn(requester);
        when(menuItemGateway.findByRestaurantNameAndOwnerLoginAndName(restaurantName, requester.getLogin(), itemName))
                .thenReturn(menuItem);

        try (var ignored = mockStatic(DeleteMenuItemUseCase.class)) {
            // Act
            deleteMenuItemByRequesterUseCase.execute(request, token);

            // Assert
            verify(tokenGateway).getRequester(token);
            verify(menuItemGateway).findByRestaurantNameAndOwnerLoginAndName(restaurantName, "owner_login", itemName);
            // We verify through indirect call to DeleteMenuItemUseCase inside the tested class.
            // Can't directly verify delete() since it’s private and created internally.
        }
    }

    @Test
    void shouldThrowException_whenMenuItemNotFound() {
        // Arrange
        String token = "mocked-token";
        String restaurantName = "NonExistent";
        String itemName = "GhostItem";

        DeleteMenuItemRequest request = new DeleteMenuItemRequest(restaurantName, itemName);
        Requester requester = new Requester("OWNER", "owner_login");

        when(tokenGateway.getRequester(token)).thenReturn(requester);
        when(menuItemGateway.findByRestaurantNameAndOwnerLoginAndName(restaurantName, requester.getLogin(), itemName))
                .thenThrow(new MenuItemNotFoundException());

        // Act & Assert
        assertThatThrownBy(() -> deleteMenuItemByRequesterUseCase.execute(request, token))
                .isInstanceOf(MenuItemNotFoundException.class);

        verify(tokenGateway).getRequester(token);
        verify(menuItemGateway).findByRestaurantNameAndOwnerLoginAndName(restaurantName, "owner_login", itemName);
        verifyNoMoreInteractions(menuItemGateway);
    }
}
