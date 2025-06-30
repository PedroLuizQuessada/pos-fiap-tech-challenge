package com.example.tech_challenge.presenters;

import com.example.tech_challenge.dtos.responses.AddressResponse;
import com.example.tech_challenge.entities.Address;

public class AddressPresenter {

    private AddressPresenter(){}

    public static AddressResponse toResponse(Address address) {
        return new AddressResponse(null, address.getState(), address.getCity(), address.getStreet(),
                address.getNumber(), address.getZipCode(), address.getAditionalInfo());
    }

    public static AddressResponse toAdminResponse(Address address) {
        return new AddressResponse(address.getId(), address.getState(), address.getCity(), address.getStreet(),
                address.getNumber(), address.getZipCode(), address.getAditionalInfo());
    }
}
