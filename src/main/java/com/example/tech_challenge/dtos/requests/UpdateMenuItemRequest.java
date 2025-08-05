package com.example.tech_challenge.dtos.requests;

public record UpdateMenuItemRequest(String restaurant, String oldName, String name, String description, Double price,
                                    Boolean availableOnline, String picture) implements UpdateMenuItem {
}
