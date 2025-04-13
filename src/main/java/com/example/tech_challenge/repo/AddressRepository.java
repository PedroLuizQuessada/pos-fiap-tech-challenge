package com.example.tech_challenge.repo;

import com.example.tech_challenge.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Address a WHERE a.id = :id")
//    void deleteById(@Param("id") Integer id);
}
