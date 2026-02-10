package ssurent.ssurentbe.domain.item.dto.response;

import java.util.List;

public record AdminItemResponse(
        Long categoryId,
        String categoryName,
        List<ItemResponse> items
) {
}
