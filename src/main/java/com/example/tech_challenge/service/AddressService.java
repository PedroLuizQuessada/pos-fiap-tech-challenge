package com.example.tech_challenge.service;

import com.example.tech_challenge.repo.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public void deleteById(Integer id) {
        addressRepository.deleteById(id);
    }
}
