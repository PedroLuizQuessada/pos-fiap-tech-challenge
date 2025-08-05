package com.example.tech_challenge.usecases.deletemenuitem;

import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.exceptions.MenuItemNotFoundException;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DeleteMenuItemUseCaseTest {

    private MenuItemGateway menuItemGateway;
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        deleteMenuItemUseCase = new DeleteMenuItemUseCase(menuItemGateway);
    }

    @Test
    void shouldDeleteMenuItemSuccessfully_whenIdIsValid() {
        // Arrange
        Long menuItemId = 1L;
        MenuItem menuItem = mock(MenuItem.class);
        MenuItemDto menuItemDto = mock(MenuItemDto.class);

        when(menuItemGateway.findMenuItemById(menuItemId)).thenReturn(menuItem);

        try (MockedStatic<MenuItemMapper> mockedStatic = mockStatic(MenuItemMapper.class)) {
            mockedStatic.when(() -> MenuItemMapper.toDto(menuItem)).thenReturn(menuItemDto);

            // Act
            deleteMenuItemUseCase.execute(menuItemId);

            // Assert
            verify(menuItemGateway).findMenuItemById(menuItemId);
            mockedStatic.verify(() -> MenuItemMapper.toDto(menuItem));
            verify(menuItemGateway).deleteMenuItem(menuItemDto);
        }
    }

    @Test
    void shouldThrowMenuItemNotFoundException_whenMenuItemDoesNotExist() {
        // Arrange
        Long menuItemId = 999L;
        when(menuItemGateway.findMenuItemById(menuItemId)).thenThrow(new MenuItemNotFoundException());

        // Act & Assert
        assertThatThrownBy(() -> deleteMenuItemUseCase.execute(menuItemId))
                .isInstanceOf(MenuItemNotFoundException.class);

        verify(menuItemGateway).findMenuItemById(menuItemId);
        verifyNoMoreInteractions(menuItemGateway);
    }
}
