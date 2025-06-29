package com.example.tech_challenge.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

public record UserTypeResponse(@JsonInclude(JsonInclude.Include.NON_NULL) Long id, String name) {
}
