package com.example.tech_challenge.infraestructure.persistence.jpa.repos;

import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.mappers.AddressJpaDtoMapper;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.AddressJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AddressRepositoryJpaImpl implements AddressDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AddressJpaDtoMapper addressMapper;

    @Override
    @Transactional
    public void deleteAddress(AddressDto addressDto) {
        AddressJpa addressJpa = addressMapper.toAddressJpa(addressDto);
        addressJpa = entityManager.merge(addressJpa);
        entityManager.remove(addressJpa);
    }
}
