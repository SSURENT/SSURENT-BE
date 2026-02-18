package ssurent.ssurentbe.domain.item.controller.docs.items;

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
import ssurent.ssurentbe.domain.item.dto.request.AdminItemCreateRequest;
import ssurent.ssurentbe.domain.item.dto.request.AdminItemUpdateRequest;
import ssurent.ssurentbe.domain.item.dto.response.AdminItemNameSearchResponse;
import ssurent.ssurentbe.domain.item.dto.response.AdminItemResponse;
import ssurent.ssurentbe.domain.item.dto.response.ItemResponse;

@Tag(name = "Item-Admin", description = "관리자 아이템 API")
public interface ItemAdminApiDocs {
    @Operation(summary = "물품 조회", description = "카테고리 ID가 있을 경우 카테고리 기반으로, 없을 경우 모든 카테고리의 아이템을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "카테고리 ID가 있으면 해당 카테고리의 물품 목록(ItemResponse[])" +
                            ", 없으면 전체 물품 현황(AdminItemResponse[])을 반환합니다."
            ),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> getItems(
            UserDetails userDetails,
            Long categoryId);

    @Operation(summary = "물품 상태 수정", description = "물품의 Status(활성화,비활성화 여부)를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "물품 상태 변경 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class))
                    )),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> updateItem(
            UserDetails userDetails,
            AdminItemUpdateRequest request);

    @Operation(summary = "물품 생성", description = "물품을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "물품 생성 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> createItem(
            UserDetails userDetails,
            AdminItemCreateRequest request);

    @Operation(summary = "물품 키워드 검색", description = "키워드 기반으로 물품을 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "물품 검색 결과 반환",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = AdminItemNameSearchResponse.class))
                    )),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> searchItem(
            UserDetails userDetails,
            String keyword);

}
