package com.example.tech_challenge.domain.user;

import com.example.tech_challenge.domain.address.Address;
import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.interfaces.EntityInterface;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Address address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityEnum authority;

    @Override
    public UserResponse entityToResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setName(name);
        userResponse.setEmail(email);
        userResponse.setLogin(login);
        userResponse.setLastUpdateDate(lastUpdateDate);
        if (!Objects.isNull(address))
            userResponse.setAddress(address.entityToResponse());
        userResponse.setAuthority(authority);

        return userResponse;
    }
}
