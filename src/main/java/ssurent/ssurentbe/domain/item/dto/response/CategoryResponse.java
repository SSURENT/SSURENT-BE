package ssurent.ssurentbe.domain.item.dto.response;

import ssurent.ssurentbe.domain.item.entity.Category;

public record CategoryResponse(
        Long categoryId,
        String categoryName
) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
