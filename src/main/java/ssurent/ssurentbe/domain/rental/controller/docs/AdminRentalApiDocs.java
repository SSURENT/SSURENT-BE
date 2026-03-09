package ssurent.ssurentbe.domain.rental.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.domain.rental.dto.request.AdminRentalReturnRequest;

import java.time.LocalDate;

@Tag(name = "Admin - Rental", description = "관리자 물품 대여 API")
public interface AdminRentalApiDocs {

    @Operation(
            summary = "전체 대여 타임라인 조회",
            description = "기간 내 모든 대여 이력을 시간 순으로 조회합니다. 각 대여는 대여/반납 이벤트로 분리되며, 미반납 건은 대여 이벤트만 포함됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전체 대여 타임라인 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 날짜 범위",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> getAllRentalTimeline(
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "조회 시작일 (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "조회 종료일 (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    @Operation(
            summary = "강제 반납",
            description = "관리자가 특정 대여를 강제로 반납 처리합니다. 반납 도우미는 최고관리자로 기록됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "강제 반납 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "대여 내역 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 반납된 대여",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> adminForceReturn(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AdminRentalReturnRequest request
    );
}
