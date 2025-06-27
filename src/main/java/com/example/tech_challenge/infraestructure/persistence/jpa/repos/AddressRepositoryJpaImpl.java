package com.example.tech_challenge.infraestructure.persistence.jpa.repos;

import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.dtos.AddressDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AddressRepositoryJpaImpl implements AddressDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deleteAddress(AddressDto addressDto) {
        Query query = entityManager.createQuery("DELETE FROM AddressJpa address WHERE address.id = :id");
        query.setParameter("id", addressDto.id());
        query.executeUpdate();
    }
}
