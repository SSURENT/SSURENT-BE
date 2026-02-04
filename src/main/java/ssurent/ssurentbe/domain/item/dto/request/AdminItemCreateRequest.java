package ssurent.ssurentbe.domain.item.dto.request;

public record AdminItemCreateRequest(
        String categoryName,
        Integer itemNum
) {
}
