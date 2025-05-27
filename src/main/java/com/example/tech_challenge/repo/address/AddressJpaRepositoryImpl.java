package com.example.tech_challenge.repo.address;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.address.persistence.jpa.AddressJpa;
import com.example.tech_challenge.mapper.persistence.AddressJpaMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AddressJpaRepositoryImpl implements AddressRepository {

    @Autowired
    private AddressJpaMapper addressJpaMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Address address) {
        AddressJpa addressMysql = addressJpaMapper.map(address);
        entityManager.merge(addressMysql).toEntity();
    }

    @Override
    @Transactional
    public void delete(Address address) {
        AddressJpa addressMysql = addressJpaMapper.map(address);
        addressMysql = entityManager.merge(addressMysql);
        entityManager.remove(addressMysql);
    }
}
