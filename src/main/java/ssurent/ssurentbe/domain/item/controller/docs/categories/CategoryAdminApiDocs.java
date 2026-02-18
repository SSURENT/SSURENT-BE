package ssurent.ssurentbe.domain.item.controller.docs.categories;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.domain.item.dto.request.AdminCategoryCreateRequest;
import ssurent.ssurentbe.domain.item.dto.response.CategoryResponse;


@Tag(name = "Category", description = "카테고리 API")
public interface CategoryAdminApiDocs {
    @Operation(summary = "카테고리 조회", description = "카테고리 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))
                    )),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> getCategories(
            UserDetails userDetails
    );

    @Operation(summary = "카테고리 추가", description = "카테고리를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 추가 성공",
                    content = @Content(
                            schema = @Schema(implementation = CategoryResponse.class)
                    )),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "409", description = "카테고리 이름 중복",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))
            )
    })
    ResponseEntity<BaseResponse<?>> createCategory(
            UserDetails userDetails,
            AdminCategoryCreateRequest request
    );

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 삭제 성공",
                    content = @Content(
                            schema = @Schema(implementation = CategoryResponse.class)
                    )),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "ID에 해당하는 카테고리 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))
            )
    })
    ResponseEntity<BaseResponse<?>> deleteCategory(
            UserDetails userDetails,
            Long categoryId
    );
}
