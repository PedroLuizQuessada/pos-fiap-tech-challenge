package com.example.tech_challenge.infraestructure.persistence.jpa.mappers;

import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.RestaurantJpa;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
@Profile("jpa")
public class RestaurantJpaDtoMapper {

    private final AddressJpaDtoMapper addressJpaDtoMapper;
    private final UserJpaDtoMapper userJpaDtoMapper;

    public RestaurantJpa toRestaurantJpa(RestaurantDto restaurantDto) {
        return new RestaurantJpa(restaurantDto.id(), restaurantDto.name(),
                !Objects.isNull(restaurantDto.address()) ? addressJpaDtoMapper.toAddressJpa(restaurantDto.address()) : null,
                restaurantDto.kitchenType(), restaurantDto.openingHours(),
                !Objects.isNull(restaurantDto.owner()) ? userJpaDtoMapper.toUserJpa(restaurantDto.owner()) : null);
    }

    public RestaurantDto toRestaurantDto(RestaurantJpa restaurantJpa) {
        return new RestaurantDto(restaurantJpa.getId(), restaurantJpa.getName(),
                !Objects.isNull(restaurantJpa.getAddressJpa()) ? addressJpaDtoMapper.toAddressDto(restaurantJpa.getAddressJpa()) : null,
                restaurantJpa.getKitchenType(), restaurantJpa.getOpeningHours(),
                !Objects.isNull(restaurantJpa.getUserJpa()) ? userJpaDtoMapper.toUserDto(restaurantJpa.getUserJpa()) : null);
    }
}
