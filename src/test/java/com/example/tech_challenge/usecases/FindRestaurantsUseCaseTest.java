package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.helper.RestaurantHelper;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindRestaurantsUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private FindRestaurantsUseCase findRestaurantsUseCase;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(restaurantGateway.findRestaurants(anyInt(), anyInt())).thenReturn(List.of(RestaurantHelper.getValidRestaurant()));

        List<Restaurant> response = findRestaurantsUseCase.execute(anyInt(), anyInt());

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UserTypeHelper.getValidUserType().getId(), response.getFirst().getId());
    }
}
