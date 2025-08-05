package com.example.tech_challenge.usecases.findrestaurantsbyowner;

import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.enums.UserTypeEnum;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.gateways.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FindRestaurantsByOwnerByRequesterUseCaseTest {

    private RestaurantGateway restaurantGateway;
    private UserGateway userGateway;
    private TokenGateway tokenGateway;
    private FindRestaurantsByOwnerByRequesterUseCase useCase;

    @BeforeEach
    void setup() {
        restaurantGateway = mock(RestaurantGateway.class);
        userGateway = mock(UserGateway.class);
        tokenGateway = mock(TokenGateway.class);
        useCase = new FindRestaurantsByOwnerByRequesterUseCase(restaurantGateway, userGateway, tokenGateway);
    }

    @Test
    void shouldReturnRestaurantsOwnedByRequester() {
        // Arrange
        int page = 0;
        int size = 10;
        String token = "valid.token";

        String login = "lucas";
        Long userId = 42L;

        UserType userType = new UserType(UserTypeEnum.OWNER.getId(), UserTypeEnum.OWNER.name());
        Address address = new Address(1L, "SP", "São Paulo", "Rua A", "123", "01234-000", null);
        User user = new User(userId, "Lucas", "lucas@email.com", login, "senha123", new Date(Date.from(Instant.now()).getTime()), address, userType, false);

        Requester requester = new Requester(userType.getName(), login);

        Restaurant restaurant1 = new Restaurant(1L, "Churrasco Brasil", address, "Brasileira", "10:00 22:00", user);
        Restaurant restaurant2 = new Restaurant(2L, "Pasta e Basta", address, "Italiana", "10:00 22:00", user);
        List<Restaurant> restaurantList = List.of(restaurant1, restaurant2);

        when(tokenGateway.getRequester(token)).thenReturn(requester);
        when(userGateway.findUserByLogin(login)).thenReturn(user);
        when(restaurantGateway.findRestaurantsByOwner(page, size, userId)).thenReturn(restaurantList);

        // Act
        List<Restaurant> result = useCase.execute(page, size, token);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Churrasco Brasil", result.get(0).getName());
        assertEquals("Pasta e Basta", result.get(1).getName());

        verify(tokenGateway).getRequester(token);
        verify(userGateway).findUserByLogin(login);
        verify(restaurantGateway).findRestaurantsByOwner(page, size, userId);
    }
}
