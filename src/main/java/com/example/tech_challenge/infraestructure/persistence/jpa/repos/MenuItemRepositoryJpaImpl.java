package com.example.tech_challenge.infraestructure.persistence.jpa.repos;

import com.example.tech_challenge.datasources.MenuItemDataSource;
import com.example.tech_challenge.dtos.MenuItemDto;
import com.example.tech_challenge.infraestructure.persistence.jpa.mappers.MenuItemJpaDtoMapper;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.MenuItemJpa;
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

    @Override
    public List<MenuItemDto> findByRestaurantNameAndOwnerLogin(int page, int size, String restaurant, String ownerLogin) {
        Query query = entityManager.createQuery("SELECT menuItem FROM MenuItemJpa menuItem WHERE menuItem.restaurantJpa.name = :restaurant AND menuItem.restaurantJpa.userJpa.login = :ownerLogin ORDER BY menuItem.id");
        query.setParameter("restaurant", restaurant);
        query.setParameter("ownerLogin", ownerLogin);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<MenuItemJpa> menuItemJpaList = query.getResultList();
        return menuItemJpaList.stream().map(userTypeJpa -> menuItemJpaDtoMapper.toMenuItemDto(userTypeJpa)).toList();
    }

    @Override
    public List<MenuItemDto> findByRestaurant(int page, int size, Long restaurant) {
        Query query = entityManager.createQuery("SELECT menuItem FROM MenuItemJpa menuItem WHERE menuItem.restaurantJpa.id = :restaurant ORDER BY menuItem.id");
        query.setParameter("restaurant", restaurant);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<MenuItemJpa> menuItemJpaList = query.getResultList();
        return menuItemJpaList.stream().map(userTypeJpa -> menuItemJpaDtoMapper.toMenuItemDto(userTypeJpa)).toList();
    }

    @Override
    public Optional<MenuItemDto> findByRestaurantNameAndOwnerLoginAndName(String restaurantName, String ownerLogin, String name) {
        Query query = entityManager.createQuery("SELECT menuItem FROM MenuItemJpa menuItem WHERE menuItem.restaurantJpa.name = :restaurantName AND menuItem.restaurantJpa.userJpa.login = :ownerLogin AND menuItem.name = :name");
        query.setParameter("restaurantName", restaurantName);
        query.setParameter("ownerLogin", ownerLogin);
        query.setParameter("name", name);
        try {
            MenuItemJpa menuItemJpa = (MenuItemJpa) query.getSingleResult();
            return Optional.ofNullable(menuItemJpaDtoMapper.toMenuItemDto(menuItemJpa));
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MenuItemDto> findById(Long id) {
        Optional<MenuItemJpa> optionalMenuItemJpa = Optional.ofNullable(entityManager.find(MenuItemJpa.class, id));
        return optionalMenuItemJpa.map(menuItemJpaDtoMapper::toMenuItemDto);
    }

    @Override
    @Transactional
    public MenuItemDto updateMenuItem(MenuItemDto menuItemDto) {
        MenuItemJpa menuItemJpa = menuItemJpaDtoMapper.toMenuItemJpa(menuItemDto);
        menuItemJpa = entityManager.merge(menuItemJpa);
        return menuItemJpaDtoMapper.toMenuItemDto(menuItemJpa);
    }

    @Override
    @Transactional
    public void deleteMenuItem(MenuItemDto menuItemDto) {
        Query query = entityManager.createQuery("DELETE FROM MenuItemJpa menuItem WHERE menuItem.id = :id");
        query.setParameter("id", menuItemDto.id());
        query.executeUpdate();
    }
}
