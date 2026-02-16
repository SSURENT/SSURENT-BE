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
import ssurent.ssurentbe.domain.item.dto.response.ItemResponse;
import ssurent.ssurentbe.domain.item.service.ItemCommandService;
import ssurent.ssurentbe.domain.item.service.ItemQueryService;

import java.util.List;

@RestController
@RequestMapping("v1/admin/items")
@RequiredArgsConstructor
public class ItemAdminController implements ItemAdminApiDocs {
    private final ItemCommandService itemCommandService;
    private final ItemQueryService itemQueryService;

    @GetMapping()
    @Override
    public ResponseEntity<BaseResponse<?>> getItems(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long categoryId) {
        List<ItemResponse> items =  itemQueryService.getAllItems(categoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS,items));
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
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS));
    }


}
