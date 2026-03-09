package ssurent.ssurentbe.domain.rental.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.domain.rental.dto.request.AdminRentalReturnRequest;

@Tag(name = "Admin - Rental", description = "관리자 물품 대여 API")
public interface AdminRentalApiDocs {

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
