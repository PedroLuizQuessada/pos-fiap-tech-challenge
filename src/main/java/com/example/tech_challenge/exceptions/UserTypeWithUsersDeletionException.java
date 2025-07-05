package com.example.tech_challenge.exceptions;

public class UserTypeWithUsersDeletionException extends RuntimeException {
    public UserTypeWithUsersDeletionException(Long numUsers) {
        super(String.format("O tipo de usuário não pode ser apagado pois existem %d usuários associados a ele", numUsers));
    }
}
