package ssurent.ssurentbe.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.item.dto.request.AdminCategoryCreateRequest;
import ssurent.ssurentbe.domain.item.dto.response.CategoryResponse;
import ssurent.ssurentbe.domain.item.entity.Category;
import ssurent.ssurentbe.domain.item.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse createCategories(AdminCategoryCreateRequest request) {
        if(categoryRepository.existsByName(request.categoryName())) {
            throw new GeneralException(ErrorStatus.DUPLICATE_CATEGORY_NAME);
        }

        Category category = Category.builder()
                .name(request.categoryName())
                .build();

        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CATEGORY_NOT_FOUND));

        // soft delete
        category.softDelete();
        categoryRepository.save(category);
    }
}
