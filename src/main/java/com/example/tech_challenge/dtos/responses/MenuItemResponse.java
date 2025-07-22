package com.example.tech_challenge.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

public record MenuItemResponse(@JsonInclude(JsonInclude.Include.NON_NULL) Long id, RestaurantResponse restaurant, String name,
                               String description, Double price, Boolean availableOnline, String picture) {
}
