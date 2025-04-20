package com.example.tech_challenge.domain.user.dto.response;

import com.example.tech_challenge.domain.address.dto.response.AddressResponse;
import com.example.tech_challenge.enums.AuthorityEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private String name;

    private String email;

    private String login;

    private Date lastUpdateDate;

    private AddressResponse address;

    private AuthorityEnum authority;
}
