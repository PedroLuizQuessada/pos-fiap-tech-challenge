package com.example.tech_challenge.dtos.requests;

public record AdminUpdateMenuItemRequest(String name, String description, Double price, Boolean availableOnline,
                                         String picture) implements UpdateMenuItem {
}
