package ssurent.ssurentbe.common.auth.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.auth.dto.request.LoginRequest;
import ssurent.ssurentbe.common.auth.dto.request.PasswordResetRequest;
import ssurent.ssurentbe.common.auth.dto.request.SignupRequest;
import ssurent.ssurentbe.common.auth.dto.request.SmsSendRequest;
import ssurent.ssurentbe.common.auth.dto.request.SmsVerifyRequest;
import ssurent.ssurentbe.common.auth.dto.response.SmsVerifyResponse;
import ssurent.ssurentbe.common.auth.dto.response.TokenResponse;
import ssurent.ssurentbe.common.auth.dto.response.TokenResponseWrapper;

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

    @Operation(summary = "로그아웃", description = "액세스 토큰으로 로그아웃합니다. Redis에 저장된 리프레시 토큰을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<Void>> logout(Authentication authentication);

    @Operation(summary = "SMS 인증번호 발송", description = "입력한 전화번호로 6자리 인증번호를 발송합니다. 해당 번호로 가입된 계정이 있어야 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증번호 발송 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 전화번호로 가입된 사용자 없음",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "SMS 발송 실패",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<Void>> sendSmsCode(@RequestBody SmsSendRequest request);

    @Operation(summary = "SMS 인증번호 검증", description = "발송된 인증번호를 검증합니다. 성공 시 비밀번호 재설정용 토큰(10분 유효)을 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증번호 검증 성공, resetToken 반환"),
            @ApiResponse(responseCode = "401", description = "인증번호 불일치 또는 만료",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<SmsVerifyResponse>> verifySmsCode(@RequestBody SmsVerifyRequest request);

    @Operation(summary = "비밀번호 재설정", description = "SMS 인증 후 발급된 resetToken으로 비밀번호를 재설정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 재설정 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않거나 만료된 resetToken",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    ResponseEntity<BaseResponse<Void>> resetPassword(@RequestBody PasswordResetRequest request);
}