package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.responses.RequesterResponse;
import com.example.tech_challenge.entities.Requester;

public class RequesterMapper {

    private RequesterMapper(){}

    public static RequesterResponse toResponse(Requester requester) {
        return new RequesterResponse(requester.getUserType(), requester.getLogin());
    }
}
