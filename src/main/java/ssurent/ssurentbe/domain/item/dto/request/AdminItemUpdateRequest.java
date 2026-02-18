package ssurent.ssurentbe.domain.item.dto.request;

import ssurent.ssurentbe.domain.item.enums.Status;

import java.util.List;

public record AdminItemUpdateRequest(
        List<ItemUpdate> itemUpdates
) {
    public record ItemUpdate(
            Long itemId,
            Status status
    ){
    }
}
