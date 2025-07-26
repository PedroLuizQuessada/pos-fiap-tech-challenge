package com.example.tech_challenge.usecases.deleterestaurant;

import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;
import com.example.tech_challenge.mappers.RestaurantMapper;

import java.util.List;
import java.util.Objects;

public class DeleteRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final AddressGateway addressGateway;
    private final MenuItemGateway menuItemGateway;

    public DeleteRestaurantUseCase(RestaurantGateway restaurantGateway, AddressGateway addressGateway, MenuItemGateway menuItemGateway) {
        this.restaurantGateway = restaurantGateway;
        this.addressGateway = addressGateway;
        this.menuItemGateway = menuItemGateway;
    }

    public void execute(Long id) {
        Restaurant restaurant = restaurantGateway.findRestaurantById(id);
        deleteRestaurant(RestaurantMapper.toDto(restaurant));
    }

    void deleteRestaurant(RestaurantDto restaurantDto) {
        List<MenuItem> menuItemList = menuItemGateway.findMenuItensByRestaurant(restaurantDto.id());

        for (MenuItem menuItem : menuItemList) {
            menuItemGateway.deleteMenuItem(MenuItemMapper.toDto(menuItem));
        }

        restaurantGateway.deleteRestaurant(restaurantDto);

        if (!Objects.isNull(restaurantDto.address()))
            addressGateway.delete(restaurantDto.address());
    }

}
