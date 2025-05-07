package com.example.tech_challenge.domain.address.entity;

import com.example.tech_challenge.domain.user.entity.UserDB;
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
public class AddressDB {

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

    @OneToOne(mappedBy = "addressDB")
    private UserDB userDB;

    public Address toEntity() {
        return new Address(id, state, city, street, number, zipCode, aditionalInfo);
    }
}
