package com.example.tech_challenge.domain.address.dto.request;

import lombok.Getter;

@Getter
public class AddressRequest {

    private String state;

    private String city;

    private String street;

    private String number;

    private String zipCode;

    private String aditionalInfo;
}
