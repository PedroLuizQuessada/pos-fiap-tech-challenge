package com.example.tech_challenge.infraestructure.persistence.jpa.repos;

import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.dtos.RestaurantDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.mappers.RestaurantJpaDtoMapper;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.RestaurantJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
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
}
