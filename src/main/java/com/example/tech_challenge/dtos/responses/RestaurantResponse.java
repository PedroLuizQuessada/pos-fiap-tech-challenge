package com.example.tech_challenge.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

public record RestaurantResponse(@JsonInclude(JsonInclude.Include.NON_NULL) Long id, String name, AddressResponse addressResponse,
                                 String kitchenType, String openingHours, UserResponse userResponse) {
}
