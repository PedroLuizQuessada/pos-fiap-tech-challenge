package com.example.tech_challenge.domain.user.persistence.jpa;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.address.persistence.jpa.AddressJpa;
import com.example.tech_challenge.domain.user.entity.User;
import com.example.tech_challenge.enums.AuthorityEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJpa implements com.example.tech_challenge.domain.interfaces.Persistence<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 45)
    private String name;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(45) COLLATE utf8_bin")
    private String login;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) COLLATE utf8_bin")
    private String password;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private AddressJpa addressJpa;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityEnum authority;

    @Override
    public User toEntity() {
        Address address = Objects.isNull(this.addressJpa) ? null : this.addressJpa.toEntity();
        return new User(id, name, email, login, password, lastUpdateDate, address, authority);
    }
}
