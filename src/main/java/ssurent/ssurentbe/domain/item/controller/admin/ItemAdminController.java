package ssurent.ssurentbe.domain.item.controller.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.item.controller.docs.items.ItemAdminApiDocs;
import ssurent.ssurentbe.domain.item.dto.request.AdminItemCreateRequest;
import ssurent.ssurentbe.domain.item.dto.request.AdminItemUpdateRequest;
import ssurent.ssurentbe.domain.item.dto.response.AdminItemNameSearchResponse;
import ssurent.ssurentbe.domain.item.dto.response.AdminItemResponse;
import ssurent.ssurentbe.domain.item.dto.response.ItemResponse;
import ssurent.ssurentbe.domain.item.service.ItemCommandService;
import ssurent.ssurentbe.domain.item.service.ItemQueryService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("v1/admin/items")
@RequiredArgsConstructor
public class ItemAdminController implements ItemAdminApiDocs {
    private final ItemCommandService itemCommandService;
    private final ItemQueryService itemQueryService;

    @GetMapping()
    public ResponseEntity<BaseResponse<?>> getItems(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            // 카테고리별 조회
            List<ItemResponse> responses = itemQueryService.getAllItemsByCategory(categoryId);
            return ResponseEntity.ok(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS, responses));
        } else {
            // 전체 조회
            List<AdminItemResponse> responses = itemQueryService.getAllItems();
            return ResponseEntity.ok(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS, responses));
        }
    }

    @PatchMapping
    @Override
    public ResponseEntity<BaseResponse<?>> updateItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AdminItemUpdateRequest request) {
        List<ItemResponse> responses = itemCommandService.updateItemsStatus(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS,responses));
    }

    @Override
    @PostMapping()
    public ResponseEntity<BaseResponse<?>> createItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AdminItemCreateRequest request) {
        itemCommandService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(SuccessStatus.COMM_CREATE_STATUS));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<?>> searchItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.ok(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS));
        }

        String trimmed = keyword.trim();
        if (trimmed.length() < 2) {
            return ResponseEntity.ok(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS));
        }

        List<AdminItemNameSearchResponse> responses = itemQueryService.searchByName(trimmed);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS,responses));
    }


}
