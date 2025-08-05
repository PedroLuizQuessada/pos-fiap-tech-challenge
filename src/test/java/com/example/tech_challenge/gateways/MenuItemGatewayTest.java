package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.MenuItemDataSource;
import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.exceptions.MenuItemNotFoundException;
import com.example.tech_challenge.mappers.MenuItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemGatewayTest {

    private MenuItemDataSource menuItemDataSource;
    private MenuItemGateway menuItemGateway;

    @BeforeEach
    void setUp() {
        menuItemDataSource = mock(MenuItemDataSource.class);
        menuItemGateway = new MenuItemGateway(menuItemDataSource);
    }

    @Test
    void shouldCreateMenuItem() {
        MenuItemDto dto = mock(MenuItemDto.class);
        MenuItem entity = mock(MenuItem.class);

        when(menuItemDataSource.createMenuItem(dto)).thenReturn(dto);

        try (MockedStatic<MenuItemMapper> mocked = mockStatic(MenuItemMapper.class)) {
            mocked.when(() -> MenuItemMapper.toEntity(dto)).thenReturn(entity);

            MenuItem result = menuItemGateway.createMenuItem(dto);
            assertThat(result).isSameAs(entity);
        }
    }

    @Test
    void shouldCountByNameAndRestaurant() {
        when(menuItemDataSource.countByNameAndRestaurant("Pizza", 1L)).thenReturn(3L);
        Long result = menuItemGateway.countByNameAndRestaurant("Pizza", 1L);
        assertThat(result).isEqualTo(3L);
    }

    @Test
    void shouldFindMenuItemsByRestaurantName() {
        MenuItemDto dto = mock(MenuItemDto.class);
        MenuItem entity = mock(MenuItem.class);

        when(menuItemDataSource.findByRestaurantName(0, 5, "Burger King")).thenReturn(List.of(dto));

        try (MockedStatic<MenuItemMapper> mocked = mockStatic(MenuItemMapper.class)) {
            mocked.when(() -> MenuItemMapper.toEntity(dto)).thenReturn(entity);

            List<MenuItem> result = menuItemGateway.findMenuItensByRestaurant(0, 5, "Burger King");
            assertThat(result).containsExactly(entity);
        }
    }

    @Test
    void shouldFindMenuItemsByRestaurantId() {
        MenuItemDto dto = mock(MenuItemDto.class);
        MenuItem entity = mock(MenuItem.class);

        when(menuItemDataSource.findByRestaurant(1, 10, 99L)).thenReturn(List.of(dto));

        try (MockedStatic<MenuItemMapper> mocked = mockStatic(MenuItemMapper.class)) {
            mocked.when(() -> MenuItemMapper.toEntity(dto)).thenReturn(entity);

            List<MenuItem> result = menuItemGateway.findMenuItensByRestaurant(1, 10, 99L);
            assertThat(result).containsExactly(entity);
        }
    }

    @Test
    void shouldFindMenuItemByRestaurantNameAndOwnerLoginAndName() {
        MenuItemDto dto = mock(MenuItemDto.class);
        MenuItem entity = mock(MenuItem.class);

        when(menuItemDataSource.findByRestaurantNameAndOwnerLoginAndName("KFC", "owner1", "Fries"))
                .thenReturn(Optional.of(dto));

        try (MockedStatic<MenuItemMapper> mocked = mockStatic(MenuItemMapper.class)) {
            mocked.when(() -> MenuItemMapper.toEntity(dto)).thenReturn(entity);

            MenuItem result = menuItemGateway.findByRestaurantNameAndOwnerLoginAndName("KFC", "owner1", "Fries");
            assertThat(result).isSameAs(entity);
        }
    }

    @Test
    void shouldThrowWhenMenuItemNotFoundByRestaurantAndOwnerAndName() {
        when(menuItemDataSource.findByRestaurantNameAndOwnerLoginAndName("KFC", "owner1", "Unknown"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                menuItemGateway.findByRestaurantNameAndOwnerLoginAndName("KFC", "owner1", "Unknown")
        ).isInstanceOf(MenuItemNotFoundException.class);
    }

    @Test
    void shouldFindMenuItemById() {
        MenuItemDto dto = mock(MenuItemDto.class);
        MenuItem entity = mock(MenuItem.class);

        when(menuItemDataSource.findById(42L)).thenReturn(Optional.of(dto));

        try (MockedStatic<MenuItemMapper> mocked = mockStatic(MenuItemMapper.class)) {
            mocked.when(() -> MenuItemMapper.toEntity(dto)).thenReturn(entity);

            MenuItem result = menuItemGateway.findMenuItemById(42L);
            assertThat(result).isSameAs(entity);
        }
    }

    @Test
    void shouldThrowWhenMenuItemNotFoundById() {
        when(menuItemDataSource.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                menuItemGateway.findMenuItemById(999L)
        ).isInstanceOf(MenuItemNotFoundException.class);
    }

    @Test
    void shouldUpdateMenuItem() {
        MenuItemDto dto = mock(MenuItemDto.class);
        MenuItem entity = mock(MenuItem.class);

        when(menuItemDataSource.updateMenuItem(dto)).thenReturn(dto);

        try (MockedStatic<MenuItemMapper> mocked = mockStatic(MenuItemMapper.class)) {
            mocked.when(() -> MenuItemMapper.toEntity(dto)).thenReturn(entity);

            MenuItem result = menuItemGateway.updateMenuItem(dto);
            assertThat(result).isSameAs(entity);
        }
    }

    @Test
    void shouldDeleteMenuItem() {
        MenuItemDto dto = mock(MenuItemDto.class);
        menuItemGateway.deleteMenuItem(dto);
        verify(menuItemDataSource).deleteMenuItem(dto);
    }
}
