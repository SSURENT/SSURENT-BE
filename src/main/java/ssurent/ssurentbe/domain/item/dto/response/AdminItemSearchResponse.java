package ssurent.ssurentbe.domain.item.dto.response;

import ssurent.ssurentbe.domain.item.enums.Condition;

public record AdminItemSearchResponse(
        Long itemId,
        String itemName,
        Condition condition
) {
}
