package com.example.tech_challenge.usecases.deletemenuitem;

import com.example.tech_challenge.dtos.requests.DeleteMenuItemRequest;
import com.example.tech_challenge.entities.MenuItem;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.gateways.MenuItemGateway;
import com.example.tech_challenge.gateways.TokenGateway;

public class DeleteMenuItemByRequesterUseCase {

    private final MenuItemGateway menuItemGateway;
    private final TokenGateway tokenGateway;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;

    public DeleteMenuItemByRequesterUseCase(MenuItemGateway menuItemGateway, TokenGateway tokenGateway) {
        this.menuItemGateway = menuItemGateway;
        this.tokenGateway = tokenGateway;
        this.deleteMenuItemUseCase = new DeleteMenuItemUseCase(menuItemGateway);
    }

    public void execute(DeleteMenuItemRequest request, String token) {
        Requester requester = tokenGateway.getRequester(token);
        MenuItem menuItem = menuItemGateway.findMenuItensByRestaurantAndOwnerLoginAndName(request.restaurant(), requester.getLogin(), request.name());
        deleteMenuItemUseCase.delete(menuItem);
    }

}
