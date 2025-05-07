package com.example.tech_challenge.domain.user.entity;

import com.example.tech_challenge.domain.address.entity.AddressDB;
import com.example.tech_challenge.enums.AuthorityEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 45)
    private String name;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, unique = true, length = 45)
    private String login;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private AddressDB addressDB;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityEnum authority;

    public User toEntity() {
        return new User(id, name, email, login, password, lastUpdateDate, addressDB.toEntity(), authority);
    }
}
