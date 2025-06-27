package com.example.tech_challenge.infraestructure.persistence.jpa.repos;

import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.mappers.UserJpaDtoMapper;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.UserJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UserRepositoryJpaImpl implements UserDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserJpaDtoMapper userJpaDtoMapper;

    @Override
    public Optional<UserDto> findUserByLogin(String login) {
        Query query = entityManager.createQuery("SELECT user FROM UserJpa user WHERE user.login = :login");
        query.setParameter("login", login);
        try {
            UserJpa userJpa = (UserJpa) query.getSingleResult();
            return Optional.ofNullable(userJpaDtoMapper.toUserDto(userJpa));
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        UserJpa userJpa = userJpaDtoMapper.toUserJpa(userDto);
        userJpa = entityManager.merge(userJpa);
        return userJpaDtoMapper.toUserDto(userJpa);
    }

    @Override
    public Long countByEmail(String email) {
        Query query = entityManager.createQuery("SELECT count(*) FROM UserJpa user WHERE user.email = :email");
        query.setParameter("email", email);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long countByLogin(String login) {
        Query query = entityManager.createQuery("SELECT count(*) FROM UserJpa user WHERE user.login = :login");
        query.setParameter("login", login);
        return (Long) query.getSingleResult();
    }

    @Override
    public Optional<UserDto> findUserById(Long id) {
        Optional<UserJpa> optionalUserJpa = Optional.ofNullable(entityManager.find(UserJpa.class, id));
        return optionalUserJpa.map(userJpaDtoMapper::toUserDto);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        UserJpa userJpa = userJpaDtoMapper.toUserJpa(userDto);
        userJpa = entityManager.merge(userJpa);
        return userJpaDtoMapper.toUserDto(userJpa);
    }

    @Override
    @Transactional
    public void deleteUser(UserDto userDto) {
        Query query = entityManager.createQuery("DELETE FROM UserJpa user WHERE user.id = :id");
        query.setParameter("id", userDto.id());
        query.executeUpdate();
    }
}
