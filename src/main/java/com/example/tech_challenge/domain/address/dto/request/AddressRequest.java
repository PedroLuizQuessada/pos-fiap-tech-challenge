package com.example.tech_challenge.domain.address.dto.request;

import com.example.tech_challenge.domain.address.Address;
import com.example.tech_challenge.interfaces.RequestInterface;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AddressRequest implements RequestInterface {

    @NotEmpty(message = "O endereço deve possuir um estado")
    @Size(max = 45, message = "O estado do endereço deve possuir até 45 caracteres")
    private String state;

    @NotEmpty(message = "O endereço deve possuir uma cidade")
    @Size(max = 45, message = "A cidade do endereço deve possuir até 45 caracteres")
    private String city;

    @NotEmpty(message = "O endereço deve possuir uma rua")
    @Size(max = 45, message = "A rua do endereço deve possuir até 45 caracteres")
    private String street;

    @NotEmpty(message = "O endereço deve possuir um número")
    @Size(max = 45, message = "O número do endereço deve possuir até 45 caracteres")
    private String number;

    @NotEmpty(message = "O endereço deve possuir um CEP")
    @Size(max = 45, message = "O CEP do endereço deve possuir até 45 caracteres")
    private String zipCode;

    @Size(max = 45, message = "O complemento do endereço deve possuir até 45 caracteres")
    private String aditionalInfo;

    @Override
    public Address requestToEntity() {
        Address address = new Address();
        address.setState(this.state);
        address.setCity(this.city);
        address.setStreet(this.street);
        address.setNumber(this.number);
        address.setZipCode(this.zipCode);
        address.setAditionalInfo(this.aditionalInfo);
        return address;
    }
}
