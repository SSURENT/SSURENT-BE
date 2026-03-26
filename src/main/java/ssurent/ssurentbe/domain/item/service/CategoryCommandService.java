package ssurent.ssurentbe.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.item.dto.request.AdminCategoryCreateRequest;
import ssurent.ssurentbe.domain.item.dto.response.CategoryResponse;
import ssurent.ssurentbe.domain.item.entity.Category;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.item.enums.Condition;
import ssurent.ssurentbe.domain.item.enums.Status;
import ssurent.ssurentbe.domain.item.repository.CategoryRepository;
import ssurent.ssurentbe.domain.item.repository.ItemRepository;
import ssurent.ssurentbe.domain.rental.repository.RentalRepository;

import java.util.List;

import static ssurent.ssurentbe.domain.rental.enums.Status.RENT;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {
    private final ItemRepository itemRepository;
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

        List<Items> items = itemRepository.findAllByCategoryId(categoryId);

        // 대여 중인 아이템이 하나라도 있으면 삭제 불가
        boolean hasRentedItem = items.stream()
                .anyMatch(item -> item.getCondition().equals(Condition.RENT) || item.getCondition().equals(Condition.OVERDUE));
        if (hasRentedItem) {
            throw new GeneralException(ErrorStatus.CATEGORY_HAS_RENTED_ITEMS);
        }

        // 하위 아이템 soft delete
        items.forEach(Items::softDelete);

        // 카테고리 soft delete
        category.softDelete();
    }
}
