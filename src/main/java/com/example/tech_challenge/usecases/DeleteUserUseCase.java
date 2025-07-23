package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.AddressGateway;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.RestaurantGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.mappers.MenuItemMapper;
import com.example.tech_challenge.mappers.RestaurantMapper;
import com.example.tech_challenge.mappers.UserMapper;

import java.util.List;
import java.util.Objects;

public class DeleteUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;
    private final RestaurantGateway restaurantGateway;
    private final MenuItemGateway menuItemGateway;

    public DeleteUserUseCase(UserGateway userGateway, AddressGateway addressGateway, RestaurantGateway restaurantGateway, MenuItemGateway menuItemGateway) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
        this.restaurantGateway = restaurantGateway;
        this.menuItemGateway = menuItemGateway;
    }

    public void execute(String login) {
        User user = userGateway.findUserByLogin(login);
        deleteUser(UserMapper.toDto(user));
    }

    public void execute(Long id) {
        User user = userGateway.findUserById(id);
        deleteUser(UserMapper.toDto(user));
    }

    private void deleteUser(UserDto userDto) {
        List<Restaurant> restaurantDtoList = restaurantGateway.findRestaurantsByOwner(userDto.id());

        for (Restaurant restaurant : restaurantDtoList) {
            RestaurantDto restaurantDto = RestaurantMapper.toDto(restaurant);

            List<MenuItem> menuItemList = menuItemGateway.findMenuItensByRestaurant(restaurant.getId());

            for (MenuItem menuItem : menuItemList) {
                menuItemGateway.deleteMenuItem(MenuItemMapper.toDto(menuItem));
            }

            restaurantGateway.deleteRestaurant(restaurantDto);

            if (!Objects.isNull(restaurant.getAddress()))
                addressGateway.delete(restaurantDto.address());
        }

        userGateway.deleteUser(userDto);
        if (!Objects.isNull(userDto.address()))
            addressGateway.delete(userDto.address());
    }
}
