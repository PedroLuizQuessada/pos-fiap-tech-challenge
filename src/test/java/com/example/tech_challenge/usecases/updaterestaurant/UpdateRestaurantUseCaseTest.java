package com.example.tech_challenge.usecases.updaterestaurant;

import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.dtos.requests.AddressRequest;
import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.entities.*;
import com.example.tech_challenge.exceptions.RestaurantNameAlreadyInUseException;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateRestaurantUseCaseTest {

    private RestaurantGateway restaurantGateway;
    private AddressGateway addressGateway;
    private UpdateRestaurantUseCase useCase;

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(RestaurantGateway.class);
        addressGateway = mock(AddressGateway.class);
        useCase = new UpdateRestaurantUseCase(restaurantGateway, addressGateway);
    }

    @Test
    void shouldUpdateRestaurantSuccessfully() {
        // Arrange
        Long restaurantId = 1L;
        Address oldAddress = new Address(10L, "SP", "São Paulo", "Rua A", "100", "01234567", null);
        Address newAddress = new Address(10L, "SP", "São Paulo", "Rua B", "200", "01234567", "Apt 2");

        UserType userType = new UserType(1L, "OWNER");
        User owner = new User(1L, "João", "joao@email.com", "joao123", "senhaSegura", new Date(System.currentTimeMillis()), oldAddress, userType, false);

        Restaurant restaurant = new Restaurant(1L, "Rest A", oldAddress, "Brasileira", "08:00 18:00", owner);

        RestaurantRequest updateRequest = new RestaurantRequest(
                "Rest B",
                new AddressRequest("SP", "São Paulo", "Rua B", "200", "01234567", "Apt 2"),
                "Italiana",
                "10:00 22:00"
        );

        Restaurant updatedRestaurant = new Restaurant(1L, "Rest B", newAddress, "Italiana", "10:00 22:00", owner);

        when(restaurantGateway.findRestaurantById(restaurantId)).thenReturn(restaurant);
        when(restaurantGateway.countByName("Rest B")).thenReturn(0L);
        when(restaurantGateway.updateRestaurant(any(RestaurantDto.class))).thenReturn(updatedRestaurant);

        // Act
        Restaurant result = useCase.execute(updateRequest, restaurantId);

        // Assert
        assertNotNull(result);
        assertEquals("Rest B", result.getName());
        assertEquals("Italiana", result.getKitchenType());
        assertEquals("10:00 22:00", result.getOpeningHours());
        assertEquals("Rua B", result.getAddress().getStreet());

        verify(restaurantGateway).findRestaurantById(restaurantId);
        verify(restaurantGateway).countByName("Rest B");
        verify(restaurantGateway).updateRestaurant(any(RestaurantDto.class));
    }

    @Test
    void shouldDeleteOldAddressIfNewAddressIsNull() {
        // Arrange
        Long restaurantId = 1L;
        Address oldAddress = new Address(20L, "RJ", "Rio", "Av. Atlântica", "500", "22222222", "Cobertura");

        UserType userType = new UserType(1L, "OWNER");
        User owner = new User(1L, "Maria", "maria@email.com", "maria123", "senha123", new Date(System.currentTimeMillis()), oldAddress, userType, false);

        Restaurant restaurant = new Restaurant(1L, "Rest X", oldAddress, "Mexicana", "09:00 17:00", owner);

        RestaurantRequest updateRequest = new RestaurantRequest(
                "Rest X",
                null,
                "Mexicana",
                "09:00 17:00"
        );

        when(restaurantGateway.findRestaurantById(restaurantId)).thenReturn(restaurant);
        when(restaurantGateway.updateRestaurant(any())).thenReturn(restaurant);

        // Act
        Restaurant result = useCase.execute(updateRequest, restaurantId);

        // Assert
        assertNotNull(result);
        verify(addressGateway).delete(any(AddressDto.class));
    }

    @Test
    void shouldThrowExceptionIfNameAlreadyInUse() {
        // Arrange
        Long restaurantId = 2L;
        Address address = new Address(30L, "MG", "Belo Horizonte", "Rua Flor", "10", "33333333", null);
        UserType userType = new UserType(2L, "OWNER");
        User owner = new User(2L, "Carlos", "carlos@mail.com", "carlos123", "senha456", new Date(System.currentTimeMillis()), address, userType, false);

        Restaurant restaurant = new Restaurant(2L, "Cantina", address, "Italiana", "08:00 18:00", owner);

        RestaurantRequest updateRequest = new RestaurantRequest(
                "Já Existe",
                null,
                "Italiana",
                "08:00 18:00"
        );

        when(restaurantGateway.findRestaurantById(restaurantId)).thenReturn(restaurant);
        when(restaurantGateway.countByName("Já Existe")).thenReturn(1L);

        // Act & Assert
        assertThrows(RestaurantNameAlreadyInUseException.class, () -> useCase.execute(updateRequest, restaurantId));

        verify(restaurantGateway, never()).updateRestaurant(any());
    }
}
