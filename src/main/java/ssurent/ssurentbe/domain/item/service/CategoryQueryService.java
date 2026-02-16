package ssurent.ssurentbe.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssurent.ssurentbe.domain.item.dto.response.CategoryResponse;
import ssurent.ssurentbe.domain.item.entity.Category;
import ssurent.ssurentbe.domain.item.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(CategoryResponse::from)
                .toList();
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAllIncludingDeleted();
        return categoryList.stream()
                .map(CategoryResponse::from)
                .toList();
    }
}
