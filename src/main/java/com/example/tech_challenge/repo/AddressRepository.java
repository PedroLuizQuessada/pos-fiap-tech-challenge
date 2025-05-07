package com.example.tech_challenge.repo;

import com.example.tech_challenge.domain.address.entity.AddressDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressDB, Integer> {
}
