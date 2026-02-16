package ssurent.ssurentbe.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssurent.ssurentbe.domain.item.dto.response.ItemResponse;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.item.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemQueryService {
    private final ItemRepository itemRepository;

    public List<ItemResponse> getItems(Long categoryId) {
        List<Items> items = itemRepository.findByCategoryId(categoryId);

        return items.stream()
                .map(ItemResponse::from)
                .toList();
    }
}
