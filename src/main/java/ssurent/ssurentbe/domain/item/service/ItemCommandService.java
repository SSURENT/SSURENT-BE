package ssurent.ssurentbe.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.item.dto.request.AdminItemCreateRequest;
import ssurent.ssurentbe.domain.item.dto.request.AdminItemUpdateRequest;
import ssurent.ssurentbe.domain.item.dto.response.ItemResponse;
import ssurent.ssurentbe.domain.item.entity.Category;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.item.repository.CategoryRepository;
import ssurent.ssurentbe.domain.item.repository.ItemRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ItemCommandService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<ItemResponse> updateItemsStatus(AdminItemUpdateRequest request) {
        List<Long> itemIds = request.itemUpdates().stream()
                .map(AdminItemUpdateRequest.ItemUpdate::itemId)
                .distinct()
                .toList();

        List<Items> items = itemRepository.findAllById(itemIds);

        if(items.size() != itemIds.size()) {
            throw new GeneralException(ErrorStatus.ITEM_NOT_FOUND);
        }

        Map<Long, Items> itemMap;
        itemMap = items.stream()
                .collect(Collectors.toMap(Items::getId, item -> item));

        for(AdminItemUpdateRequest.ItemUpdate update : request.itemUpdates()) {
            Items item = itemMap.get(update.itemId());
            item.updateStatus(update.status());
        }

        return items.stream()
                .map(ItemResponse::from)
                .toList();
    }

    @Transactional
    public void createItem(AdminItemCreateRequest request) {
        // request 의 categoryName = 우산
        // request 의 itemNum = 101
        // item 중 우산(101)이 있으면 안됌
        // categoryName == 우산 AND itemNum == 101 인 애가 있는지 검사
        // index없다면 풀스캔, 둘중 cardinalty가 높은건 itemNum,
        // itemNum -> categoryName 순서로 타게 하기(인덱스가 없다면 일단...)
        if(itemRepository.existsByItemNumAndName(request.itemNum(), request.categoryName())){
            throw new GeneralException(ErrorStatus.DUPLICATE_ITEM);
        }

        Category category = categoryRepository.findByName(request.categoryName())
                .orElseThrow(() -> new GeneralException(ErrorStatus.CATEGORY_NOT_FOUND));

        Items item = Items.builder()
                .name(request.categoryName())
                .itemNum(request.itemNum())
                .categoryId(category)
                .build();

        itemRepository.save(item);
    }
}
