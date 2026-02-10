package ssurent.ssurentbe.domain.item.dto.request;

import java.util.List;

public record AdminItemUpdateRequest(
        List<ItemUpdate> itemUpdates
) {
    public record ItemUpdate(
            Long itemId,
            String status
    ){
    }
}
