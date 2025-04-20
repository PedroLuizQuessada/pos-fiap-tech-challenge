package com.example.tech_challenge.component.mapper;

import com.example.tech_challenge.domain.address.Address;
import com.example.tech_challenge.domain.address.dto.request.AddressRequest;
import com.example.tech_challenge.domain.address.dto.response.AddressResponse;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toAddressEntity(AddressRequest addressRequest) {
        Address address = new Address();
        address.setState(addressRequest.getState());
        address.setCity(addressRequest.getCity());
        address.setStreet(addressRequest.getStreet());
        address.setNumber(addressRequest.getNumber());
        address.setZipCode(addressRequest.getZipCode());
        address.setAditionalInfo(addressRequest.getAditionalInfo());
        return address;
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
