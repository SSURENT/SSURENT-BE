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
import ssurent.ssurentbe.domain.rental.dto.request.RentalExtendRequest;
import ssurent.ssurentbe.domain.rental.dto.request.RentalReportRequest;
import ssurent.ssurentbe.domain.rental.dto.request.RentalRequest;
import ssurent.ssurentbe.domain.rental.dto.request.RentalReturnRequest;
import java.util.List;

@Tag(name = "Rental", description = "물품 대여 API")
public interface RentalApiDocs {

    @Operation(
            summary = "물품 대여",
            description = "물품을 대여합니다. 대여 기간은 7일이며, 대여 즉시 물품 상태가 비활성화됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "물품 대여 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "물품 또는 도우미를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 대여 중인 물품",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> createRental(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RentalRequest request
    );

    @Operation(summary = "내 대여 목록 조회", description = "로그인한 사용자의 현재 대여 중인 물품 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 대여 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "대여 내역 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> getMyRentals(
            @AuthenticationPrincipal UserDetails userDetails
    );

    @Operation(summary = "대여 기한 연장", description = "대여 반납 예정일을 3일 연장합니다. 1회만 연장 가능합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "대여 기한 연장 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "403", description = "본인의 대여가 아님",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "대여 내역 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 연장된 대여",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> extendRental(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RentalExtendRequest request
    );

    @Operation(summary = "물품 반납", description = "대여 중인 물품을 반납합니다. 반납 예정일이 지난 경우 연체 처리됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "물품 반납 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "403", description = "본인의 대여가 아님",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "대여 내역 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 반납된 대여",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> returnRental(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RentalReturnRequest request
    );

    @Operation(summary = "물품 문제 신고", description = "대여 중인 물품의 문제를 신고합니다. 문제 유형: DAMAGE(파손), LOSS(분실), MALFUNCTION(기능불량), OTHER(기타). OTHER 선택 시 description 필수. 신고 후 반납 처리는 되지 않습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "문제 신고 접수 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "기타 유형 선택 시 description 미입력",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "403", description = "본인의 대여가 아님",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "대여 내역 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 반납된 대여",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> reportRental(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RentalReportRequest request
    );
}
