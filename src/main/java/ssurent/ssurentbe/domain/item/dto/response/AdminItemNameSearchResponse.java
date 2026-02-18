package ssurent.ssurentbe.domain.item.dto.response;

import ssurent.ssurentbe.domain.item.entity.Items;

public record AdminItemNameSearchResponse(
        Long itemId,
        String itemName
) {
    public static AdminItemNameSearchResponse from(Items item){
        String ItemName = item.getName() + "(" + item.getItemNum() + ")";


        return new AdminItemNameSearchResponse(
                item.getId(),
                ItemName
        );
    }
}
