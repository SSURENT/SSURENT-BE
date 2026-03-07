package ssurent.ssurentbe.domain.users.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.domain.users.dto.request.AdminUserPenaltyCreateRequest;
import ssurent.ssurentbe.domain.users.dto.request.AdminUserStatusUpdateRequest;
import ssurent.ssurentbe.domain.users.dto.response.AdminUserDetailResponse;
import ssurent.ssurentbe.domain.users.dto.response.AdminUserResponse;

import java.util.List;

@Tag(name = "User-Admin", description = "관리자 유저 API")
public interface AdminUserApiDocs {
    @Operation(summary = "원하는 사용자 목록 조회", description = "관리자가 전체/정지/관리자안 사용자를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = AdminUserResponse.class))
                    )),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<List<AdminUserResponse>>> getUsersByStatus(String status);

    @Operation(summary = "사용자 ID 기반 조회", description = "관리자가 PathVariable로 사용자 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = AdminUserDetailResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<AdminUserDetailResponse>> getUserDetails(Long userId);

    @Operation(summary = "사용자 상태 변경", description = "관리자가 사용자 상태를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "변경 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> changeStatus(AdminUserStatusUpdateRequest request);

    @Operation(summary = "사용자 징계 추가", description = "관리자가 사용자 징계를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "추가 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> createPenalty(AdminUserPenaltyCreateRequest request);

    @Operation(summary = "사용자 징계 삭제", description = "관리자가 사용자 징계를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<?>> deletePenalty(Long penaltyId);
}
