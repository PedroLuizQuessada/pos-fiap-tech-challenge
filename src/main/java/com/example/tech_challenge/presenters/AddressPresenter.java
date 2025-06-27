package com.example.tech_challenge.presenters;

import com.example.tech_challenge.dtos.responses.AddressResponse;
import com.example.tech_challenge.entities.Address;

public class AddressPresenter {

    public static AddressResponse toResponse(Address address) {
        return new AddressResponse(address.getState(), address.getCity(), address.getStreet(),
                address.getNumber(), address.getZipCode(), address.getAditionalInfo());
    }
}
