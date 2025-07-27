package com.example.tech_challenge.usecases.updatemenuitem;

import com.example.tech_challenge.dtos.requests.UpdateMenuItemRequest;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.TokenGateway;

public class UpdateMenuItemByRequesterUseCase {

    private final MenuItemGateway menuItemGateway;
    private final TokenGateway tokenGateway;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;

    public UpdateMenuItemByRequesterUseCase(MenuItemGateway menuItemGateway, TokenGateway tokenGateway) {
        this.menuItemGateway = menuItemGateway;
        this.tokenGateway = tokenGateway;
        this.updateMenuItemUseCase = new UpdateMenuItemUseCase(menuItemGateway);
    }

    public MenuItem execute(UpdateMenuItemRequest updateRequest, String token) {
        Requester requester = tokenGateway.getRequester(token);
        MenuItem menuItem = menuItemGateway.findByRestaurantNameAndOwnerLoginAndName(updateRequest.restaurant(),
                requester.getLogin(), updateRequest.oldName());
        return updateMenuItemUseCase.update(menuItem, updateRequest);
    }

}
