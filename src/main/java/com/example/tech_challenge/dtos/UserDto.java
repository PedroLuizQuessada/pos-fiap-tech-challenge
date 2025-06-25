package com.example.tech_challenge.dtos;

import com.example.tech_challenge.enums.AuthorityEnum;

import java.sql.Date;

public record UserDto(Long id, String name, String email, String login, String password, Date lastUpdateDate,
                      AddressDto addressDto, AuthorityEnum authority) {
}
