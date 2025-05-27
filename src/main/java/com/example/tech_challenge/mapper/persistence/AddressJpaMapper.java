package com.example.tech_challenge.mapper.persistence;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.address.persistence.jpa.AddressJpa;
import org.springframework.stereotype.Component;

@Component
public class AddressJpaMapper implements PersistenceMapper<AddressJpa, Address> {
    @Override
    public AddressJpa map(Address entity) {
        AddressJpa addressJpa = new AddressJpa();
        addressJpa.setId(entity.getId());
        addressJpa.setState(entity.getState());
        addressJpa.setCity(entity.getCity());
        addressJpa.setStreet(entity.getStreet());
        addressJpa.setNumber(entity.getNumber());
        addressJpa.setZipCode(entity.getZipCode());
        addressJpa.setAditionalInfo(entity.getAditionalInfo());
        return addressJpa;
    }
}
