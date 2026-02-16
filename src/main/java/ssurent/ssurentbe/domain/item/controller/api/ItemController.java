package ssurent.ssurentbe.domain.item.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.item.controller.docs.items.ItemApiDocs;
import ssurent.ssurentbe.domain.item.dto.response.ItemResponse;
import ssurent.ssurentbe.domain.item.service.ItemCommandService;
import ssurent.ssurentbe.domain.item.service.ItemQueryService;

import java.util.List;

@RestController
@RequestMapping("v1/api/items")
@RequiredArgsConstructor
public class ItemController implements ItemApiDocs {
    private final ItemCommandService itemCommandService;
    private final ItemQueryService itemQueryService;

    @GetMapping()
    @Override
    public ResponseEntity<BaseResponse<?>> getActiveItems(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long categoryId) {
        List<ItemResponse> items =  itemQueryService.getActiveItems(categoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS,items));
    }
}
