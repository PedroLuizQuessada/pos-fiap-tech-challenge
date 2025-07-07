package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.dtos.responses.UserTypeResponse;
import com.example.tech_challenge.entities.UserType;

public class UserTypeMapper {

    private UserTypeMapper() {}

    public static UserType toEntity(UserTypeDto userTypeDto) {
        return new UserType(userTypeDto.id(), userTypeDto.name());
    }

    public static UserType toEntity(UserTypeRequest userTypeDto) {
        return new UserType(null, userTypeDto.name());
    }

    public static UserTypeDto toDto(UserType userType) {
        return new UserTypeDto(userType.getId(), userType.getName());
    }

    public static UserTypeResponse toResponse(UserType userType) {
        return new UserTypeResponse(null, userType.getName());
    }

    public static UserTypeResponse toAdminResponse(UserType userType) {
        return new UserTypeResponse(userType.getId(), userType.getName());
    }
}
