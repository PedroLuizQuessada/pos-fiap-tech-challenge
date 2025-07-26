package com.example.tech_challenge.infraestructure.persistence.jpa.repos;

import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.mappers.RestaurantJpaDtoMapper;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.RestaurantJpa;
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
public class RestaurantRepositoryJpaImpl implements RestaurantDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestaurantJpaDtoMapper restaurantJpaDtoMapper;

    @Override
    @Transactional
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        RestaurantJpa restaurantJpa = restaurantJpaDtoMapper.toRestaurantJpa(restaurantDto);
        restaurantJpa = entityManager.merge(restaurantJpa);
        return restaurantJpaDtoMapper.toRestaurantDto(restaurantJpa);
    }

    @Override
    public Long countByName(String name) {
        Query query = entityManager.createQuery("SELECT count(*) FROM RestaurantJpa restaurant WHERE restaurant.name = :name");
        query.setParameter("name", name);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<RestaurantDto> findRestaurantsByOwner(Long ownerId) {
        Query query = entityManager.createQuery("SELECT restaurant FROM RestaurantJpa restaurant WHERE restaurant.userJpa.id = :ownerId ORDER BY restaurant.id");
        query.setParameter("ownerId", ownerId);
        List<RestaurantJpa> restaurantJpaList = query.getResultList();
        return restaurantJpaList.stream().map(restaurantJpa -> restaurantJpaDtoMapper.toRestaurantDto(restaurantJpa)).toList();
    }

    @Override
    public Optional<RestaurantDto> findRestaurantByNameAndOwnerLogin(String name, String ownerLogin) {
        Query query = entityManager.createQuery("SELECT restaurant FROM RestaurantJpa restaurant WHERE restaurant.name = :name AND restaurant.userJpa.login = :login");
        query.setParameter("name", name);
        query.setParameter("login", ownerLogin);
        try {
            RestaurantJpa restaurantJpa = (RestaurantJpa) query.getSingleResult();
            return Optional.ofNullable(restaurantJpaDtoMapper.toRestaurantDto(restaurantJpa));
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RestaurantDto> findRestaurantById(Long id) {
        Optional<RestaurantJpa> optionalRestaurantJpa = Optional.ofNullable(entityManager.find(RestaurantJpa.class, id));
        return optionalRestaurantJpa.map(restaurantJpaDtoMapper::toRestaurantDto);
    }

    @Override
    public Optional<RestaurantDto> findRestaurantByIdAndOwnerLogin(Long id, String ownerLogin) {
        Query query = entityManager.createQuery("SELECT restaurant FROM RestaurantJpa restaurant WHERE restaurant.id = :id AND restaurant.userJpa.login = :login");
        query.setParameter("id", id);
        query.setParameter("login", ownerLogin);
        try {
            RestaurantJpa restaurantJpa = (RestaurantJpa) query.getSingleResult();
            return Optional.ofNullable(restaurantJpaDtoMapper.toRestaurantDto(restaurantJpa));
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public RestaurantDto updateRestaurant(RestaurantDto restaurantDto) {
        RestaurantJpa restaurantJpa = restaurantJpaDtoMapper.toRestaurantJpa(restaurantDto);
        restaurantJpa = entityManager.merge(restaurantJpa);
        return restaurantJpaDtoMapper.toRestaurantDto(restaurantJpa);
    }

    @Override
    @Transactional
    public void deleteRestaurant(RestaurantDto restaurantDto) {
        Query query = entityManager.createQuery("DELETE FROM RestaurantJpa restaurant WHERE restaurant.id = :id");
        query.setParameter("id", restaurantDto.id());
        query.executeUpdate();
    }
}
