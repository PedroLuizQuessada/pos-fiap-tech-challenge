package com.example.tech_challenge.infraestructure.persistence.jpa.mappers;

import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.AddressJpa;
import org.springframework.stereotype.Component;

@Component
public class AddressJpaDtoMapper {

    public AddressJpa toAddressJpa(AddressDto addressDto) {
        return new AddressJpa(addressDto.id(), addressDto.state(), addressDto.city(), addressDto.street(), addressDto.number(),
                addressDto.zipCode(), addressDto.aditionalInfo());
    }

    public AddressDto toAddressDto(AddressJpa addressJpa) {
        return new AddressDto(addressJpa.getId(), addressJpa.getState(), addressJpa.getCity(), addressJpa.getStreet(),
                addressJpa.getNumber(), addressJpa.getZipCode(), addressJpa.getAditionalInfo());
    }

}
