package com.example.tech_challenge.dtos.requests;

public record RestaurantRequest(String name, AddressRequest address, String kitchenType, String openingHours) {
}
