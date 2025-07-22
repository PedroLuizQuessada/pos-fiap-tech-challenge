package com.example.tech_challenge.dtos;

public record MenuItemDto(Long id, RestaurantDto restaurant, String name, String description, Double price, Boolean availableOnline, String picture) {
}
