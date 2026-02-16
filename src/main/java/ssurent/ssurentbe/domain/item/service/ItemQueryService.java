package ssurent.ssurentbe.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.domain.item.dto.response.AdminItemNameSearchResponse;
import ssurent.ssurentbe.domain.item.dto.response.AdminItemResponse;
import ssurent.ssurentbe.domain.item.dto.response.ItemResponse;
import ssurent.ssurentbe.domain.item.entity.Category;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.item.repository.CategoryRepository;
import ssurent.ssurentbe.domain.item.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemQueryService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<ItemResponse> getActiveItemsByCategory(Long categoryId) {
        List<Items> items = itemRepository.findActiveByCategoryId(categoryId);

        return items.stream()
                .map(ItemResponse::from)
                .toList();
    }

    @Transactional
    public List<ItemResponse> getAllItemsByCategory(Long categoryId) {
        List<Items> items = itemRepository.findAllByCategoryId(categoryId);

        return items.stream()
                .map(ItemResponse::from)
                .toList();
    }

    @Transactional
    public List<AdminItemResponse> getAllItems(){
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> {
                    List<Items> items = itemRepository.findAllByCategoryId(category.getId());

                    List<ItemResponse> itemResponses = items.stream()
                            .map(ItemResponse::from)
                            .toList();

                    return new AdminItemResponse(
                            category.getId(),
                            category.getName(),
                            itemResponses
                    );
                })
                .toList();
    }

    public List<AdminItemNameSearchResponse> searchByName(String keyword) {
        List<Items> items = itemRepository.findByNameStartingWith(keyword);

        return items.stream()
                .map(AdminItemNameSearchResponse::from)
                .toList();
    }
}
