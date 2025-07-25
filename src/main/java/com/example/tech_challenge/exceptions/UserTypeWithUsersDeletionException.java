package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.BadRequestException;

public class UserTypeWithUsersDeletionException extends BadRequestException {
    public UserTypeWithUsersDeletionException(Long numUsers) {
        super(String.format("O tipo de usuário não pode ser apagado pois existem %d usuários associados a ele", numUsers));
    }
}
