package com.example.tech_challenge.presenters;

import com.example.tech_challenge.dtos.responses.RequesterResponse;
import com.example.tech_challenge.entities.Requester;

public class RequesterPresenter {

    private RequesterPresenter(){}

    public static RequesterResponse toResponse(Requester requester) {
        return new RequesterResponse(requester.getUserType(), requester.getLogin());
    }
}
