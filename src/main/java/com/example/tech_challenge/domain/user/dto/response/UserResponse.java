package com.example.tech_challenge.domain.user.dto.response;

import com.example.tech_challenge.domain.address.dto.response.AddressResponse;
import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.interfaces.ResponseInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements ResponseInterface, Serializable {

    private String name;

    private String email;

    private String login;

    private Date lastUpdateDate;

    private AddressResponse address;

    private AuthorityEnum authority;
}
