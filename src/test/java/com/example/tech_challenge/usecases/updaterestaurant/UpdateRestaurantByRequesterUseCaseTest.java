package com.example.tech_challenge.usecases.updaterestaurant;

import com.example.tech_challenge.dtos.requests.UpdateRestaurantRequest;
import com.example.tech_challenge.entities.*;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateRestaurantByRequesterUseCaseTest {

    private RestaurantGateway restaurantGateway;
    private TokenGateway tokenGateway;
    private UpdateRestaurantByRequesterUseCase useCase;

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(RestaurantGateway.class);
        AddressGateway addressGateway = mock(AddressGateway.class);
        tokenGateway = mock(TokenGateway.class);
        useCase = new UpdateRestaurantByRequesterUseCase(restaurantGateway, addressGateway, tokenGateway);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {
        // Arrange
        String token = "token-xyz";
        String oldRestaurantName = "Inexistente";

        UpdateRestaurantRequest request = new UpdateRestaurantRequest(
                oldRestaurantName,
                "Novo Nome",
                null,
                "Mexicana",
                "10:00 22:00"
        );

        Requester requester = new Requester("donojose", "OWNER");

        when(tokenGateway.getRequester(token)).thenReturn(requester);
        when(restaurantGateway.findRestaurantByNameAndOwnerLogin(oldRestaurantName, requester.getLogin())).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> useCase.execute(request, token));

        verify(tokenGateway).getRequester(token);
        verify(restaurantGateway).findRestaurantByNameAndOwnerLogin(oldRestaurantName, requester.getLogin());
    }
}
