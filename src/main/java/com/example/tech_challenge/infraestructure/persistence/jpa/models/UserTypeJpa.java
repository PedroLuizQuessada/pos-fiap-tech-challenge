package com.example.tech_challenge.infraestructure.persistence.jpa.models;

import com.example.tech_challenge.infraestructure.exceptions.BadJpaArgumentException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "user_types")
@Getter
@NoArgsConstructor
public class UserTypeJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name", nullable = false, unique = true, length = 45)
    private String name;

    public UserTypeJpa(Long id, String name) {

        String message = "";

        try {
            validateName(name);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadJpaArgumentException(message);

        this.id = id;
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name))
            throw new BadJpaArgumentException("O tipo de usuário deve possuir um nome para ser armazenado no banco de dados.");

        if (name.length() > 45)
            throw new BadJpaArgumentException("O nome do tipo de usuário deve possuir até 45 caracteres para ser armazenado no banco de dados.");
    }
}
