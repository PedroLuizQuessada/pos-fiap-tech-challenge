package com.example.tech_challenge.mapper.entity;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.address.dto.request.AddressRequest;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toAddressEntity(AddressRequest addressRequest) {
        return new Address(addressRequest.state(), addressRequest.city(), addressRequest.street(),
                addressRequest.number(), addressRequest.zipCode(), addressRequest.aditionalInfo());
    }

    public Address toAddressEntity(AddressRequest addressRequest, Long id) {
        return new Address(id, addressRequest.state(), addressRequest.city(), addressRequest.street(),
                addressRequest.number(), addressRequest.zipCode(), addressRequest.aditionalInfo());
    }
}
