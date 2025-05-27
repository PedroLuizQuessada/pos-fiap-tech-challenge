package com.example.tech_challenge.domain.address.persistence.jpa;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.user.persistence.jpa.UserJpa;
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
public class AddressJpa implements com.example.tech_challenge.domain.interfaces.Persistence<Address> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne(mappedBy = "addressJpa")
    private UserJpa userJpa;

    @Override
    public Address toEntity() {
        return new Address(id, state, city, street, number, zipCode, aditionalInfo);
    }
}
