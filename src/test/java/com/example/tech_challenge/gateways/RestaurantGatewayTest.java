package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.exceptions.RestaurantNotFoundException;
import com.example.tech_challenge.mappers.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantGatewayTest {

    private RestaurantDataSource restaurantDataSource;
    private RestaurantGateway restaurantGateway;

    @BeforeEach
    void setUp() {
        restaurantDataSource = mock(RestaurantDataSource.class);
        restaurantGateway = new RestaurantGateway(restaurantDataSource);
    }

    @Test
    void shouldCreateRestaurant() {
        RestaurantDto dto = mock(RestaurantDto.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantDataSource.createRestaurant(dto)).thenReturn(dto);

        try (MockedStatic<RestaurantMapper> mocked = mockStatic(RestaurantMapper.class)) {
            mocked.when(() -> RestaurantMapper.toEntity(dto)).thenReturn(restaurant);

            Restaurant result = restaurantGateway.createRestaurant(dto);
            assertThat(result).isSameAs(restaurant);
        }
    }

    @Test
    void shouldCountByName() {
        when(restaurantDataSource.countByName("Pizza Place")).thenReturn(5L);
        Long count = restaurantGateway.countByName("Pizza Place");
        assertThat(count).isEqualTo(5L);
    }

    @Test
    void shouldFindRestaurants() {
        RestaurantDto dto = mock(RestaurantDto.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantDataSource.findRestaurants(0, 10)).thenReturn(List.of(dto));

        try (MockedStatic<RestaurantMapper> mocked = mockStatic(RestaurantMapper.class)) {
            mocked.when(() -> RestaurantMapper.toEntity(dto)).thenReturn(restaurant);

            List<Restaurant> result = restaurantGateway.findRestaurants(0, 10);
            assertThat(result).containsExactly(restaurant);
        }
    }

    @Test
    void shouldFindRestaurantsByOwner() {
        RestaurantDto dto = mock(RestaurantDto.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantDataSource.findRestaurantsByOwner(0, 10, 1L)).thenReturn(List.of(dto));

        try (MockedStatic<RestaurantMapper> mocked = mockStatic(RestaurantMapper.class)) {
            mocked.when(() -> RestaurantMapper.toEntity(dto)).thenReturn(restaurant);

            List<Restaurant> result = restaurantGateway.findRestaurantsByOwner(0, 10, 1L);
            assertThat(result).containsExactly(restaurant);
        }
    }

    @Test
    void shouldFindRestaurantByNameAndOwnerLogin() {
        RestaurantDto dto = mock(RestaurantDto.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantDataSource.findRestaurantByNameAndOwnerLogin("Sushi Bar", "owner123")).thenReturn(Optional.of(dto));

        try (MockedStatic<RestaurantMapper> mocked = mockStatic(RestaurantMapper.class)) {
            mocked.when(() -> RestaurantMapper.toEntity(dto)).thenReturn(restaurant);

            Restaurant result = restaurantGateway.findRestaurantByNameAndOwnerLogin("Sushi Bar", "owner123");
            assertThat(result).isSameAs(restaurant);
        }
    }

    @Test
    void shouldThrowWhenRestaurantNotFoundByNameAndOwnerLogin() {
        when(restaurantDataSource.findRestaurantByNameAndOwnerLogin("Unknown", "user")).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                restaurantGateway.findRestaurantByNameAndOwnerLogin("Unknown", "user")
        ).isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void shouldFindRestaurantById() {
        RestaurantDto dto = mock(RestaurantDto.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantDataSource.findRestaurantById(100L)).thenReturn(Optional.of(dto));

        try (MockedStatic<RestaurantMapper> mocked = mockStatic(RestaurantMapper.class)) {
            mocked.when(() -> RestaurantMapper.toEntity(dto)).thenReturn(restaurant);

            Restaurant result = restaurantGateway.findRestaurantById(100L);
            assertThat(result).isSameAs(restaurant);
        }
    }

    @Test
    void shouldThrowWhenRestaurantNotFoundById() {
        when(restaurantDataSource.findRestaurantById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                restaurantGateway.findRestaurantById(999L)
        ).isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void shouldUpdateRestaurant() {
        RestaurantDto dto = mock(RestaurantDto.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantDataSource.updateRestaurant(dto)).thenReturn(dto);

        try (MockedStatic<RestaurantMapper> mocked = mockStatic(RestaurantMapper.class)) {
            mocked.when(() -> RestaurantMapper.toEntity(dto)).thenReturn(restaurant);

            Restaurant result = restaurantGateway.updateRestaurant(dto);
            assertThat(result).isSameAs(restaurant);
        }
    }

    @Test
    void shouldDeleteRestaurant() {
        RestaurantDto dto = mock(RestaurantDto.class);
        restaurantGateway.deleteRestaurant(dto);
        verify(restaurantDataSource).deleteRestaurant(dto);
    }
}
