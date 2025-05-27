package com.example.tech_challenge.service.address;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.repo.address.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public void save(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void delete(Address address) {
        addressRepository.delete(address);
    }
}
