package com.example.tech_challenge.domain.address;

import com.example.tech_challenge.domain.address.dto.response.AddressResponse;
import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.interfaces.EntityInterface;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address implements EntityInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address_state", nullable = false, length = 45)
    private String state;

    @Column(nullable = false, length = 45)
    private String city;

    @Column(nullable = false, length = 45)
    private String street;

    @Column(name = "address_number", nullable = false, length = 45)
    private String number;

    @Column(name = "zip_code", nullable = false, length = 45)
    private String zipCode;

    @Column(name = "aditional_info", length = 45)
    private String aditionalInfo;

    @OneToOne(mappedBy = "address")
    private User user;

    @Override
    public AddressResponse entityToResponse() {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setState(this.state);
        addressResponse.setCity(this.city);
        addressResponse.setStreet(this.street);
        addressResponse.setNumber(this.number);
        addressResponse.setZipCode(this.zipCode);
        addressResponse.setAditionalInfo(this.aditionalInfo);
        return addressResponse;
    }
}
