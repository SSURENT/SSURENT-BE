package ssurent.ssurentbe.domain.item.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.item.controller.docs.categories.CategoryApiDocs;
import ssurent.ssurentbe.domain.item.dto.response.CategoryResponse;
import ssurent.ssurentbe.domain.item.service.CategoryCommandService;
import ssurent.ssurentbe.domain.item.service.CategoryQueryService;

import java.util.List;

@RestController
@RequestMapping("v1/api/categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryApiDocs {
    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<?>> getCategories() {
        List<CategoryResponse> categories =  categoryQueryService.getCategories();
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS,categories));
    }
}
