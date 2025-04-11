package com.example.tech_challenge.domain.user;

import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.interfaces.EntityInterface;

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
public class User implements EntityInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Column(length = 255)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityEnum authority;

    //response constructor
    public User(String name, String email, String login, Date lastUpdateDate, String address, AuthorityEnum authority) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.lastUpdateDate = lastUpdateDate;
        this.address = address;
        this.authority = authority;
    }

    //new user constructor
    public User(String name, String email, String login, String password, String address, AuthorityEnum authority) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.address = address;
        this.authority = authority;
    }

    //update user constructor
    public User(String name, String email, String login, String address) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.address = address;
        this.lastUpdateDate = new Date(System.currentTimeMillis());
    }

    @Override
    public UserResponse entityToResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setName(name);
        userResponse.setEmail(email);
        userResponse.setLastUpdateDate(lastUpdateDate);
        userResponse.setAddress(address);
        userResponse.setAuthority(authority);

        return userResponse;
    }
}
