package ssurent.ssurentbe.domain.item.dto.response;

import ssurent.ssurentbe.domain.item.entity.Category;
import ssurent.ssurentbe.domain.item.entity.Items;

public record AdminItemNameSearchResponse(
        Long itemId,
        String itemName
) {
    public static AdminItemNameSearchResponse from(Items item){
        return new AdminItemNameSearchResponse(
                item.getId(),
                item.getItemName()
        );
    }
}
