package com.example.tech_challenge.dtos;

public record RestaurantDto(Long id, String name, AddressDto address, String kitchenType, String openingHours, UserDto owner) {
}
