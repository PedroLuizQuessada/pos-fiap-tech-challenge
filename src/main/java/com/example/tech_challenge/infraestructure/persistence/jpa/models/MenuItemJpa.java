package com.example.tech_challenge.infraestructure.persistence.jpa.models;

import com.example.tech_challenge.infraestructure.exceptions.BadJpaArgumentException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "menu_itens")
@Getter
@NoArgsConstructor
public class MenuItemJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant", referencedColumnName = "id", nullable = false)
    private RestaurantJpa restaurantJpa;

    @Column(name = "menu_item_name", nullable = false, unique = true, length = 45)
    private String name;

    @Column(length = 45)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(name = "available_online", nullable = false)
    private Boolean availableOnline;

    @Column(nullable = false, length = 255)
    private String picture;

    public MenuItemJpa(Long id, RestaurantJpa restaurantJpa, String name, String description, Double price, Boolean availableOnline, String picture) {
        validateRestaurant(restaurantJpa);
        validateName(name);
        validateDescription(description);
        validatePrice(price);
        validateAvailableOnline(availableOnline);
        validatePicture(picture);

        this.id = id;
        this.restaurantJpa = restaurantJpa;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnline = availableOnline;
        this.picture = picture;
    }

    private void validateRestaurant(RestaurantJpa restaurantJpa) {
        if (Objects.isNull(restaurantJpa))
            throw new BadJpaArgumentException("O item do cardápio deve possuir um restaurante para ser armazenado no banco de dados");
    }

    private void validateName(String name) {
        if (Objects.isNull(name))
            throw new BadJpaArgumentException("O item do cardápio deve possuir um nome para ser armazenado no banco de dados");

        if (name.length() > 45)
            throw new BadJpaArgumentException("O nome do item do cardápio deve possuir até 45 caracteres para ser armazenado no banco de dados");
    }

    private void validateDescription(String description) {
        if (description.length() > 45)
            throw new BadJpaArgumentException("A descrição do item do cardápio deve possuir até 255 caracteres para ser armazenado no banco de dados");
    }

    private void validatePrice(Double price) {
        if (Objects.isNull(price))
            throw new BadJpaArgumentException("O item do cardápio deve possuir um preço para ser armazenado no banco de dados");
    }

    private void validateAvailableOnline(Boolean availableOnline) {
        if (Objects.isNull(availableOnline))
            throw new BadJpaArgumentException("O item do cardápio deve possuir um status para disponibilidade on-line para ser armazenado no banco de dados");
    }

    private void validatePicture(String picture) {
        if (Objects.isNull(picture))
            throw new BadJpaArgumentException("O item do cardápio deve possuir uma imagem para ser armazenado no banco de dados");

        if (picture.length() > 255)
            throw new BadJpaArgumentException("A imagem do item do cardápio deve possuir até 255 caracteres para ser armazenado no banco de dados");
    }
}
