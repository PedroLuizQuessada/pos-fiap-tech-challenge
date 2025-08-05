package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.helper.MenuItemHelper;
import com.example.tech_challenge.helper.UserTypeHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindMenuItensByRestaurantUseCaseTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @InjectMocks
    private FindMenuItensByRestaurantUseCase findMenuItensByRestaurantUseCase;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(menuItemGateway.findMenuItensByRestaurant(anyInt(), anyInt(), anyLong())).thenReturn(List.of(MenuItemHelper.getValidMenuItem()));

        List<MenuItem> response = findMenuItensByRestaurantUseCase.execute(anyInt(), anyInt(), anyLong());

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UserTypeHelper.getValidUserType().getId(), response.getFirst().getId());
    }
}
