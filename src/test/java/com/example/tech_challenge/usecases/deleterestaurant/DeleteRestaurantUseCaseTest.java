package com.example.tech_challenge.usecases.deleterestaurant;

import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.helper.RestaurantHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DeleteRestaurantUseCaseTest {

    private RestaurantGateway restaurantGateway;
    private AddressGateway addressGateway;
    private MenuItemGateway menuItemGateway;
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @BeforeEach
    void setup() {
        addressGateway = mock(AddressGateway.class);
        restaurantGateway = mock(RestaurantGateway.class);
        menuItemGateway = mock(MenuItemGateway.class);
        deleteRestaurantUseCase = new DeleteRestaurantUseCase(restaurantGateway, addressGateway, menuItemGateway);
    }

    @Test
    public void executeWithSuccess() {
        Restaurant restaurant = RestaurantHelper.getValidRestaurant();
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(restaurant);
        when(menuItemGateway.findMenuItensByRestaurant(anyInt(), anyInt(), anyLong())).thenReturn(List.of());
        doNothing().when(restaurantGateway).deleteRestaurant(any());
        doNothing().when(addressGateway).delete(any());

        deleteRestaurantUseCase.execute(1L);

        verify(restaurantGateway, times(1)).deleteRestaurant(any());
    }

}
