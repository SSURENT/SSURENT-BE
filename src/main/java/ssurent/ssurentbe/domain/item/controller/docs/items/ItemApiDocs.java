package ssurent.ssurentbe.domain.item.controller.docs.items;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.domain.item.dto.response.ItemResponse;

@Tag(name = "Item", description = "아이템 API")
public interface ItemApiDocs
{
    @Operation(summary = "물품 조회", description = "대여 가능한 물품들을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "물품 조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ItemResponse.class))
                    )),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> getItems(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long categoryId);
}
