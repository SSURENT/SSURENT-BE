package ssurent.ssurentbe.domain.item.dto.response;

import ssurent.ssurentbe.domain.item.entity.Category;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.item.enums.Condition;
import ssurent.ssurentbe.domain.item.enums.Status;

public record ItemResponse(
        Long itemId,
        String itemName,
        String itemDescription,
        Status status,
        Condition condition
) {
    public static ItemResponse from(Items item) {
        return new ItemResponse(
                item.getId(),
                item.getItemName(),
                item.getCategoryId().getDescription(),
                item.getStatus(),
                item.getCondition()
        );
    }
}
