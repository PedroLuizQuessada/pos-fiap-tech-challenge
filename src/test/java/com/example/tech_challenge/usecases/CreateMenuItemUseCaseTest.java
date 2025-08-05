package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.CreateMenuItemRequest;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.exceptions.MenuItemNameAlreadyInUseException;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.helper.MenuItemHelper;
import com.example.tech_challenge.helper.RequesterHelper;
import com.example.tech_challenge.helper.RestaurantHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateMenuItemUseCaseTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private TokenGateway tokenGateway;

    @InjectMocks
    private CreateMenuItemUseCase createMenuItemUseCase;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(tokenGateway.getRequester(anyString())).thenReturn(RequesterHelper.getValidRequester());
        when(restaurantGateway.findRestaurantByNameAndOwnerLogin(anyString(), anyString())).thenReturn(RestaurantHelper.getValidRestaurant());
        when(menuItemGateway.countByNameAndRestaurant(anyString(), anyLong())).thenReturn(0L);
        when(menuItemGateway.createMenuItem(any())).thenReturn(MenuItemHelper.getValidMenuItem());

        CreateMenuItemRequest request = new CreateMenuItemRequest("Restaurante", "Prato",
                "Descrição", 10.10, true, "foto");
        String token = "abc123";

        MenuItem menuItem = createMenuItemUseCase.execute(request, token);

        assertNotNull(menuItem);
        assertEquals(menuItem.getRestaurant().getId(), RestaurantHelper.getValidRestaurant().getId());
        assertEquals(menuItem.getName(), MenuItemHelper.getValidMenuItem().getName());
        assertEquals(menuItem.getDescription(), MenuItemHelper.getValidMenuItem().getDescription());
        assertEquals(menuItem.getPrice(), MenuItemHelper.getValidMenuItem().getPrice());
        assertEquals(menuItem.getAvailableOnline(), MenuItemHelper.getValidMenuItem().getAvailableOnline());
        assertEquals(menuItem.getPicture(), MenuItemHelper.getValidMenuItem().getPicture());
    }

    @Test
    public void executeWithDuplicateNameShouldThrowException() {
        when(tokenGateway.getRequester(anyString())).thenReturn(RequesterHelper.getValidRequester());
        when(restaurantGateway.findRestaurantByNameAndOwnerLogin(anyString(), anyString())).thenReturn(RestaurantHelper.getValidRestaurant());
        when(menuItemGateway.countByNameAndRestaurant(anyString(), anyLong())).thenReturn(1L);

        CreateMenuItemRequest request = new CreateMenuItemRequest("Restaurante", "Prato",
                "Descrição", 10.10, true, "foto");
        String token = "abc123";

        assertThrows(MenuItemNameAlreadyInUseException.class, () -> createMenuItemUseCase.execute(request, token));
    }
}
