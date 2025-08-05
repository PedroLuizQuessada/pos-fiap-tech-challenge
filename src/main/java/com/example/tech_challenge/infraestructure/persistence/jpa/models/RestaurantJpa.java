package com.example.tech_challenge.infraestructure.persistence.jpa.models;

import com.example.tech_challenge.infraestructure.exceptions.BadJpaArgumentException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "restaurants")
@Getter
@NoArgsConstructor
public class RestaurantJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_name", unique = true, nullable = false, length = 45)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private AddressJpa addressJpa;

    @Column(name = "kitchen_type", nullable = false, length = 45)
    private String kitchenType;

    @Column(name = "opening_hours", nullable = false)
    private String openingHours;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
    private UserJpa userJpa;

    public RestaurantJpa(Long id, String name, AddressJpa addressJpa, String kitchenType, String openingHours, UserJpa userJpa) {

        String message = "";

        try {
            validateName(name);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateKitchenType(kitchenType);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateOpeningHours(openingHours);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateUserJpa(userJpa);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadJpaArgumentException(message);

        this.id = id;
        this.name = name;
        this.addressJpa = addressJpa;
        this.kitchenType = kitchenType;
        this.openingHours = openingHours;
        this.userJpa = userJpa;
    }

    private void validateName(String name) {
        if (Objects.isNull(name))
            throw new BadJpaArgumentException("O restaurante deve possuir um nome para ser armazenado no banco de dados.");

        if (name.length() > 45)
            throw new BadJpaArgumentException("O nome do restaurante deve possuir até 45 caracteres para ser armazenado no banco de dados.");
    }

    private void validateKitchenType(String kitchenType) {
        if (Objects.isNull(kitchenType))
            throw new BadJpaArgumentException("O restaurante deve possuir um tipo de cozinha para ser armazenado no banco de dados.");

        if (kitchenType.length() > 45)
            throw new BadJpaArgumentException("O tipo de cozinha do restaurante deve possuir até 45 caracteres para ser armazenado no banco de dados.");
    }

    private void validateOpeningHours(String openingHours) {
        if (Objects.isNull(openingHours))
            throw new BadJpaArgumentException("O restaurante deve possuir um horário de funcionamento para ser armazenado no banco de dados.");
    }

    private void validateUserJpa(UserJpa userJpa) {
        if (Objects.isNull(userJpa))
            throw new BadJpaArgumentException("O restaurante deve possuir um dono para ser armazenado no banco de dados.");
    }
}
