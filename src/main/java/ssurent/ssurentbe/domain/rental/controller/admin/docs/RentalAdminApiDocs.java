package ssurent.ssurentbe.domain.rental.controller.admin.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ssurent.ssurentbe.domain.rental.dto.response.AdminUserRentalHistoryResponse;

import java.time.LocalDate;

@Tag(name = "Rental-Admin", description = "관리자 대여 API")
public interface RentalAdminApiDocs {

    @Operation(
            summary = "유저 대여 내역 조회",
            description = "특정 유저의 대여 내역을 기간 및 물품명으로 필터링하여 조회합니다. " +
                    "startDate, endDate, itemName은 선택 파라미터이며, " +
                    "미입력 시 전체 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "대여 내역 조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = AdminUserRentalHistoryResponse.class))
                    )),
            @ApiResponse(responseCode = "400", description = "시작일이 종료일보다 늦음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "유저 없음 또는 대여 내역 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> getUserRentalHistory(
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(name = "userId", description = "조회할 유저 ID", required = true, example = "1")
            @RequestParam Long userId,
            @Parameter(name = "startDate", description = "조회 시작일 (yyyy-MM-dd)", example = "2025-01-01")
            @RequestParam(required = false) LocalDate startDate,
            @Parameter(name = "endDate", description = "조회 종료일 (yyyy-MM-dd)", example = "2025-12-31")
            @RequestParam(required = false) LocalDate endDate,
            @Parameter(name = "itemName", description = "물품명 키워드 (부분 일치)", example = "노트북")
            @RequestParam(required = false) String itemName
    );
}