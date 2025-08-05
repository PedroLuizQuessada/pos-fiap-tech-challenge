package com.example.tech_challenge.usecases.findrestaurantsbyowner;

import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.enums.UserTypeEnum;
import com.example.tech_challenge.gateways.RestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FindRestaurantsByOwnerUseCaseTest {

    private RestaurantGateway restaurantGateway;
    private FindRestaurantsByOwnerUseCase useCase;

    @BeforeEach
    void setup() {
        restaurantGateway = mock(RestaurantGateway.class);
        useCase = new FindRestaurantsByOwnerUseCase(restaurantGateway);
    }

    @Test
    void shouldReturnRestaurantsOwnedByUser() {
        // Arrange
        int page = 0;
        int size = 10;
        Long ownerId = 5L;

        UserType userType = new UserType(UserTypeEnum.OWNER.getId(), UserTypeEnum.OWNER.name());
        Address address = new Address(1L,"SP", "São Paulo", "Rua das Flores", "123", "01000-000", null);
        User owner = new User(ownerId, "Lucas", "lucas@email.com", "lucas", "senha1", new Date(Date.from(Instant.now()).getTime()), address, userType, false);

        Restaurant restaurant1 = new Restaurant(1L, "Restaurante A", address, "Brasileira", "10:00 22:00", owner);
        Restaurant restaurant2 = new Restaurant(2L, "Restaurante B", address, "Italiana", "10:00 22:00", owner);

        when(restaurantGateway.findRestaurantsByOwner(page, size, ownerId)).thenReturn(List.of(restaurant1, restaurant2));

        // Act
        List<Restaurant> result = useCase.execute(page, size, ownerId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Restaurante A", result.get(0).getName());
        assertEquals("Restaurante B", result.get(1).getName());
        verify(restaurantGateway).findRestaurantsByOwner(page, size, ownerId);
    }
}
