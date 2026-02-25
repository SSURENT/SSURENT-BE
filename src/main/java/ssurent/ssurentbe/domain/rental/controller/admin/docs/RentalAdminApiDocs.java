package ssurent.ssurentbe.domain.rental.controller.admin.docs;

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
            @RequestParam Long userId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String itemName
    );
}