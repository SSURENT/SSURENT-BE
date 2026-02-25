package ssurent.ssurentbe.domain.users.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.domain.users.dto.request.UpdatePhoneNumberRequest;
import ssurent.ssurentbe.domain.users.dto.response.UserInfoResponse;
import ssurent.ssurentbe.domain.users.dto.response.UserPenaltyResponse;

import java.util.List;

@Tag(name = "User", description = "사용자 API")
public interface UserApiDocs {

    @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    UserInfoResponse getMyInfo(@AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "전화번호 변경", description = "로그인한 사용자의 전화번호를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "변경 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<Void>> updatePhoneNumber(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdatePhoneNumberRequest request
    );

    @Operation(summary = "내 패널티 조회", description = "로그인한 사용자의 패널티 내역을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<List<UserPenaltyResponse>>> getMyPenalties(
            @AuthenticationPrincipal UserDetails userDetails
    );
}
