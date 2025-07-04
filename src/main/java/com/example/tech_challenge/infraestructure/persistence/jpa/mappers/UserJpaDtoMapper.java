package com.example.tech_challenge.infraestructure.persistence.jpa.mappers;

import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.UserJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UserJpaDtoMapper {

    private final AddressJpaDtoMapper addressJpaDtoMapper;
    private final UserTypeJpaDtoMapper userTypeJpaDtoMapper;

    public UserJpa toUserJpa(UserDto userDto) {
        return new UserJpa(userDto.id(), userDto.name(), userDto.email(), userDto.login(), userDto.password(), userDto.lastUpdateDate(),
                !Objects.isNull(userDto.addressDto()) ? addressJpaDtoMapper.toAddressJpa(userDto.addressDto()) : null,
                !Objects.isNull(userDto.userTypeDto()) ? userTypeJpaDtoMapper.toUserTypeJpa(userDto.userTypeDto()) : null);
    }

    public UserDto toUserDto(UserJpa userJpa) {
        return new UserDto(userJpa.getId(), userJpa.getName(), userJpa.getEmail(), userJpa.getLogin(), userJpa.getPassword(),
                userJpa.getLastUpdateDate(), !Objects.isNull(userJpa.getAddressJpa()) ? addressJpaDtoMapper.toAddressDto(userJpa.getAddressJpa()) : null,
                !Objects.isNull(userJpa.getUserTypeJpa()) ? userTypeJpaDtoMapper.toUserTypeDto(userJpa.getUserTypeJpa()) : null);
    }

}
