package com.example.tech_challenge.dtos.requests;

public record CreateMenuItemRequest(Long restaurant, String name, String description, Double price, Boolean availableOnline, String picture) {
}
