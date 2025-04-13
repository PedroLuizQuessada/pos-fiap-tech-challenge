package com.example.tech_challenge.service;

import com.example.tech_challenge.repo.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void deleteById(Integer id) {
        addressRepository.deleteById(id);
    }
}
