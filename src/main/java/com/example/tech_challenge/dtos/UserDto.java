package com.example.tech_challenge.dtos;

import java.sql.Date;

public record UserDto(Long id, String name, String email, String login, String password, Date lastUpdateDate,
                      AddressDto address, UserTypeDto userType) {
}
