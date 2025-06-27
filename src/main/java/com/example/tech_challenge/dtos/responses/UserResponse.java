package com.example.tech_challenge.dtos.responses;

import com.example.tech_challenge.enums.AuthorityEnum;

import java.sql.Date;

public record UserResponse(String name, String email, String login, Date lastUpdateDate, AddressResponse address, AuthorityEnum authority) {

}
