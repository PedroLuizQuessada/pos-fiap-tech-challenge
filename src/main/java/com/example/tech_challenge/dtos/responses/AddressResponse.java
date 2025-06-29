package com.example.tech_challenge.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

public record AddressResponse (@JsonInclude(JsonInclude.Include.NON_NULL) Long id, String state, String city, String street,
                               String number, String zipCode, String aditionalInfo) {

}
