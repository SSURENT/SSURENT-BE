package ssurent.ssurentbe.domain.item.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.item.controller.docs.categories.CategoryAdminApiDocs;
import ssurent.ssurentbe.domain.item.dto.request.AdminCategoryCreateRequest;
import ssurent.ssurentbe.domain.item.dto.response.CategoryResponse;
import ssurent.ssurentbe.domain.item.service.CategoryCommandService;
import ssurent.ssurentbe.domain.item.service.CategoryQueryService;

import java.util.List;

@RestController
@RequestMapping("v1/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController implements CategoryAdminApiDocs {
    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;


    @Override
    @GetMapping()
    public ResponseEntity<BaseResponse<?>> getCategories() {
        List<CategoryResponse> categories =  categoryQueryService.getCategories();
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS,categories));
    }

    @Override
    @PostMapping()
    public ResponseEntity<BaseResponse<?>> createCategory(
            @RequestBody AdminCategoryCreateRequest request) {
        CategoryResponse response = categoryCommandService.createCategories(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(SuccessStatus.COMM_CREATE_STATUS,response));
    }

    @Override
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<BaseResponse<?>> deleteCategory(
            @PathVariable Long categoryId) {
        categoryCommandService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.success(SuccessStatus.COMM_SUCCESS_STATUS));
    }
}
