package ssurent.ssurentbe.domain.users.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.domain.users.dto.LoginRequest;
import ssurent.ssurentbe.domain.users.dto.SignupRequest;
import ssurent.ssurentbe.domain.users.dto.TokenResponse;
import ssurent.ssurentbe.domain.users.dto.TokenResponseWrapper;

@Tag(name = "Auth", description = "인증 API")
public interface AuthApiDocs {

    @Operation(summary = "회원가입", description = "학번, 이름, 전화번호, 비밀번호로 회원가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = TokenResponseWrapper.class))),
            @ApiResponse(responseCode = "409", description = "이미 가입된 학번",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<TokenResponse>> signup(@RequestBody SignupRequest request);

    @Operation(summary = "로그인", description = "학번과 비밀번호로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = TokenResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "학번 또는 비밀번호 불일치",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "403", description = "탈퇴한 사용자",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<TokenResponse>> login(@RequestBody LoginRequest request);

    @Operation(summary = "토큰 갱신", description = "리프레시 토큰으로 새로운 액세스 토큰을 발급받습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
                    content = @Content(schema = @Schema(implementation = TokenResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<TokenResponse>> refresh(@RequestHeader("Authorization") String authorization);
}
