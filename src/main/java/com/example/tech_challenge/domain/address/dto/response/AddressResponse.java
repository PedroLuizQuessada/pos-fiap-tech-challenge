package com.example.tech_challenge.domain.address.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressResponse {

    private String state;
    private String city;
    private String street;
    private String number;
    private String zipCode;
    private String aditionalInfo;
}
