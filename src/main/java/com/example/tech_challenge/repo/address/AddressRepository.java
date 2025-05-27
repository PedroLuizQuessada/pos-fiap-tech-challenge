package com.example.tech_challenge.repo.address;

import com.example.tech_challenge.domain.address.entity.Address;

public interface AddressRepository {
    void save(Address address);
    void delete(Address address);
}
