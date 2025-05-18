package com.example.tech_challenge.service.address;

import com.example.tech_challenge.repo.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public void deleteById(Integer id) {
        addressRepository.deleteById(id);
    }
}
