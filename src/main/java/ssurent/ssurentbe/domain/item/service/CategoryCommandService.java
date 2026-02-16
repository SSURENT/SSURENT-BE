package ssurent.ssurentbe.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssurent.ssurentbe.domain.item.dto.request.AdminCategoryCreateRequest;
import ssurent.ssurentbe.domain.item.entity.Category;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {
    public void createCategories(AdminCategoryCreateRequest request) {

    }

    public void deleteCategory(Long categoryId) {
    }
}
