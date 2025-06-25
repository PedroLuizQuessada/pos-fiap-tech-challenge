package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.dtos.AddressDto;

public class AddressGateway {

    private final AddressDataSource addressDataSource;

    public AddressGateway(AddressDataSource addressDataSource) {
        this.addressDataSource = addressDataSource;
    }

    public void delete(AddressDto addressDto) {
        addressDataSource.deleteAddress(addressDto);
    }
}
