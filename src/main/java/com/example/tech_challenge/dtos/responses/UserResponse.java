package com.example.tech_challenge.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;

public record UserResponse(@JsonInclude(JsonInclude.Include.NON_NULL) Long id, String name, String email, String login,
                           Date lastUpdateDate, AddressResponse address, UserTypeResponse userTypeResponse) {

}
