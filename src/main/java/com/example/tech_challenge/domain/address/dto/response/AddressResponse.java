package com.example.tech_challenge.domain.address.dto.response;

import com.example.tech_challenge.interfaces.ResponseInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse implements ResponseInterface, Serializable {

    private String state;
    private String city;
    private String street;
    private String number;
    private String zipCode;
    private String aditionalInfo;
}
