package com.example.tech_challenge.mapper.response;

import com.example.tech_challenge.domain.address.dto.response.AddressResponse;
import com.example.tech_challenge.domain.address.entity.Address;

public class AddressResponseMapper implements ResponseMapper<AddressResponse, Address> {
    @Override
    public AddressResponse map(Address address) {
        return new AddressResponse(address.getState(), address.getCity(), address.getStreet(), address.getNumber(),
                address.getZipCode(), address.getAditionalInfo());
    }
}
