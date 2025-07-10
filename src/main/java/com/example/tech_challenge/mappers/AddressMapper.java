package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.requests.AddressRequest;
import com.example.tech_challenge.dtos.responses.AddressResponse;
import com.example.tech_challenge.entities.Address;

public class AddressMapper {

    private AddressMapper() {}

    public static Address toEntity(AddressDto addressDto) {
        return new Address(addressDto.id(), addressDto.state(), addressDto.city(), addressDto.street(),
                addressDto.number(), addressDto.zipCode(), addressDto.aditionalInfo());
    }

    public static Address toEntity(AddressRequest addressDto) {
        return new Address(null, addressDto.state(), addressDto.city(), addressDto.street(),
                addressDto.number(), addressDto.zipCode(), addressDto.aditionalInfo());
    }

    public static Address toEntity(AddressRequest addressDto, Long id) {
        return new Address(id, addressDto.state(), addressDto.city(), addressDto.street(),
                addressDto.number(), addressDto.zipCode(), addressDto.aditionalInfo());
    }

    public static AddressDto toDto(Address address) {
        return new AddressDto(address.getId(), address.getState(), address.getCity(), address.getStreet(), address.getNumber(),
                address.getZipCode(), address.getAditionalInfo());
    }

    public static AddressDto toDto(AddressRequest address) {
        return new AddressDto(null, address.state(), address.city(), address.street(), address.number(),
                address.zipCode(), address.aditionalInfo());
    }

    public static AddressResponse toResponse(Address address) {
        return new AddressResponse(null, address.getState(), address.getCity(), address.getStreet(),
                address.getNumber(), address.getZipCode(), address.getAditionalInfo());
    }

    public static AddressResponse toAdminResponse(Address address) {
        return new AddressResponse(address.getId(), address.getState(), address.getCity(), address.getStreet(),
                address.getNumber(), address.getZipCode(), address.getAditionalInfo());
    }
}
