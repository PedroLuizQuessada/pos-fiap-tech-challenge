package com.example.tech_challenge.dtos.requests;

public record UpdateRestaurantRequest(String oldName, String name, AddressRequest address, String kitchenType, String openingHours) {
}
