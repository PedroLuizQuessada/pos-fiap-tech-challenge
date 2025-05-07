package com.example.tech_challenge.component.mapper;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.address.dto.request.AddressRequest;
import com.example.tech_challenge.domain.address.dto.response.AddressResponse;
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

    public AddressResponse toAddressResponse(Address address) {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setState(address.getState());
        addressResponse.setCity(address.getCity());
        addressResponse.setStreet(address.getStreet());
        addressResponse.setNumber(address.getNumber());
        addressResponse.setZipCode(address.getZipCode());
        addressResponse.setAditionalInfo(address.getAditionalInfo());
        return addressResponse;
    }
}
