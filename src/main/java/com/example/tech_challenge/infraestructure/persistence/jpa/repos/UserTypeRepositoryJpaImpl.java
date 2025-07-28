package com.example.tech_challenge.infraestructure.persistence.jpa.repos;

import com.example.tech_challenge.datasources.UserTypeDataSource;
import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.mappers.UserTypeJpaDtoMapper;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.UserTypeJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class UserTypeRepositoryJpaImpl implements UserTypeDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserTypeJpaDtoMapper userTypeJpaDtoMapper;

    @Override
    @Transactional
    public UserTypeDto createUserType(UserTypeDto userTypeDto) {
        UserTypeJpa userTypeJpa = userTypeJpaDtoMapper.toUserTypeJpa(userTypeDto);
        userTypeJpa = entityManager.merge(userTypeJpa);
        return userTypeJpaDtoMapper.toUserTypeDto(userTypeJpa);
    }

    @Override
    @Transactional
    public UserTypeDto updateUserType(UserTypeDto userTypeDto) {
        UserTypeJpa userTypeJpa = userTypeJpaDtoMapper.toUserTypeJpa(userTypeDto);
        userTypeJpa = entityManager.merge(userTypeJpa);
        return userTypeJpaDtoMapper.toUserTypeDto(userTypeJpa);
    }

    @Override
    public Long countByName(String name) {
        Query query = entityManager.createQuery("SELECT count(*) FROM UserTypeJpa userType WHERE userType.name = :name");
        query.setParameter("name", name);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<UserTypeDto> findAllUserTypes(int page, int size) {
        Query query = entityManager.createQuery("SELECT userType FROM UserTypeJpa userType ORDER BY userType.id");
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<UserTypeJpa> userTypeJpaList = query.getResultList();
        return userTypeJpaList.stream().map(userTypeJpa -> userTypeJpaDtoMapper.toUserTypeDto(userTypeJpa)).toList();
    }

    @Override
    public Optional<UserTypeDto> findUserTypeByName(String name) {
        Query query = entityManager.createQuery("SELECT userType FROM UserTypeJpa userType WHERE userType.name = :name");
        query.setParameter("name", name);
        try {
            UserTypeJpa userTypeJpa = (UserTypeJpa) query.getSingleResult();
            return Optional.ofNullable(userTypeJpaDtoMapper.toUserTypeDto(userTypeJpa));
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserTypeDto> findUserTypeById(Long id) {
        Optional<UserTypeJpa> optionalUserTypeJpa = Optional.ofNullable(entityManager.find(UserTypeJpa.class, id));
        return optionalUserTypeJpa.map(userTypeJpaDtoMapper::toUserTypeDto);
    }

    @Override
    @Transactional
    public void deleteUserType(UserTypeDto userTypeDto) {
        Query query = entityManager.createQuery("DELETE FROM UserTypeJpa userType WHERE userType.id = :id");
        query.setParameter("id", userTypeDto.id());
        query.executeUpdate();
    }
}
