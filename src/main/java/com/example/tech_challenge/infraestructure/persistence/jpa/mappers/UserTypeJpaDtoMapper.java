package com.example.tech_challenge.infraestructure.persistence.jpa.mappers;

import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.UserTypeJpa;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("jpa")
public class UserTypeJpaDtoMapper {

    public UserTypeJpa toUserTypeJpa(UserTypeDto userTypeDto) {
        return new UserTypeJpa(userTypeDto.id(), userTypeDto.name());
    }

    public UserTypeDto toUserTypeDto(UserTypeJpa userTypeJpa) {
        return new UserTypeDto(userTypeJpa.getId(), userTypeJpa.getName());
    }
}
