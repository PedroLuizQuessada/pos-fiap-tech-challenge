package com.example.tech_challenge.component.mapper;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.address.dto.request.AddressRequest;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toAddressEntity(AddressRequest addressRequest) {
        return new Address(addressRequest.getState(), addressRequest.getCity(), addressRequest.getStreet(),
                addressRequest.getNumber(), addressRequest.getZipCode(), addressRequest.getAditionalInfo());
    }

    public Address toAddressEntity(AddressRequest addressRequest, Long id) {
        return new Address(id, addressRequest.getState(), addressRequest.getCity(), addressRequest.getStreet(),
                addressRequest.getNumber(), addressRequest.getZipCode(), addressRequest.getAditionalInfo());
    }
}
