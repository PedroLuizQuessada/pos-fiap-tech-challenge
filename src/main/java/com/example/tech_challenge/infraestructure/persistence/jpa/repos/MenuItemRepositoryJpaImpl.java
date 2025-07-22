package com.example.tech_challenge.infraestructure.persistence.jpa.repos;

import com.example.tech_challenge.datasources.MenuItemDataSource;
import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.mappers.MenuItemJpaDtoMapper;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.MenuItemJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MenuItemRepositoryJpaImpl implements MenuItemDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MenuItemJpaDtoMapper menuItemJpaDtoMapper;

    @Override
    @Transactional
    public MenuItemDto createMenuItem(MenuItemDto menuItemDto) {
        MenuItemJpa menuItemJpa = menuItemJpaDtoMapper.toMenuItemJpa(menuItemDto);
        menuItemJpa = entityManager.merge(menuItemJpa);
        return menuItemJpaDtoMapper.toMenuItemDto(menuItemJpa);
    }

    @Override
    public Long countByNameAndRestaurant(String name, Long restaurant) {
        Query query = entityManager.createQuery("SELECT count(*) FROM MenuItemJpa menuItem WHERE menuItem.name = :name AND menuItem.restaurantJpa.id = :restaurant");
        query.setParameter("name", name);
        query.setParameter("restaurant", restaurant);
        return (Long) query.getSingleResult();
    }
}
