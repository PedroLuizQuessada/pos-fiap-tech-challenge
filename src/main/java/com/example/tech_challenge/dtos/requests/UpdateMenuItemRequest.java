package com.example.tech_challenge.dtos.requests;

import com.fasterxml.jackson.annotation.JsonInclude;

public record UpdateMenuItemRequest(@JsonInclude(JsonInclude.Include.NON_NULL) Long restaurant,
                                    @JsonInclude(JsonInclude.Include.NON_NULL) String oldName,
                                    String name, String description, Double price, Boolean availableOnline, String picture) {
}
