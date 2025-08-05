package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.AddressRequest;
import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.exceptions.RestaurantNameAlreadyInUseException;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.helper.RequesterHelper;
import com.example.tech_challenge.helper.RestaurantHelper;
import com.example.tech_challenge.helper.UserHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateRestaurantUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private TokenGateway tokenGateway;

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(tokenGateway.getRequester(anyString())).thenReturn(RequesterHelper.getValidRequester());
        when(userGateway.findUserByLogin(anyString())).thenReturn(UserHelper.getValidUser());
        when(restaurantGateway.countByName(anyString())).thenReturn(0L);
        when(restaurantGateway.createRestaurant(any())).thenReturn(RestaurantHelper.getValidRestaurant());

        AddressRequest addressRequest = new AddressRequest("Estado", "Cidade", "Rua", "Número",
                "CEP", "Complemento");
        RestaurantRequest request = new RestaurantRequest("Restaurante", addressRequest,
                "Tipo de cozinha","08:00 18:00");
        String token = "abc123";

        Restaurant restaurant = createRestaurantUseCase.execute(request, token);

        assertNotNull(restaurant);
        assertEquals(restaurant.getId(), RestaurantHelper.getValidRestaurant().getId());
        assertEquals(restaurant.getName(), RestaurantHelper.getValidRestaurant().getName());
        assertEquals(restaurant.getAddress().getId(), RestaurantHelper.getValidRestaurant().getAddress().getId());
        assertEquals(restaurant.getKitchenType(), RestaurantHelper.getValidRestaurant().getKitchenType());
        assertEquals(restaurant.getOpeningHours(), RestaurantHelper.getValidRestaurant().getOpeningHours());
        assertEquals(restaurant.getOwner().getId(), RestaurantHelper.getValidRestaurant().getOwner().getId());
    }

    @Test
    public void executeWithDuplicateNameShouldThrowException() {
        when(tokenGateway.getRequester(anyString())).thenReturn(RequesterHelper.getValidRequester());
        when(userGateway.findUserByLogin(anyString())).thenReturn(UserHelper.getValidUser());
        when(restaurantGateway.countByName(anyString())).thenReturn(1L);

        AddressRequest addressRequest = new AddressRequest("Estado", "Cidade", "Rua", "Número",
                "CEP", "Complemento");
        RestaurantRequest request = new RestaurantRequest("Restaurante", addressRequest,
                "Tipo de cozinha","08:00 18:00");
        String token = "abc123";

        assertThrows(RestaurantNameAlreadyInUseException.class, () -> createRestaurantUseCase.execute(request, token));
    }

}
