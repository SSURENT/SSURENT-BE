package ssurent.ssurentbe.common.auth.controller;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.common.auth.controller.docs.AuthApiDocs;
import ssurent.ssurentbe.common.auth.service.AuthService;
import ssurent.ssurentbe.common.auth.dto.request.LoginRequest;
import ssurent.ssurentbe.common.auth.dto.request.PasswordResetRequest;
import ssurent.ssurentbe.common.auth.dto.request.SignupRequest;
import ssurent.ssurentbe.common.auth.dto.request.SmsSendRequest;
import ssurent.ssurentbe.common.auth.dto.request.SmsVerifyRequest;
import ssurent.ssurentbe.common.auth.dto.response.SmsVerifyResponse;
import ssurent.ssurentbe.common.auth.dto.response.TokenResponse;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApiDocs {

    private final AuthService authService;

    @Override
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<TokenResponse>> signup(@RequestBody @Valid SignupRequest request) {
        TokenResponse data = authService.signup(request);
        SuccessStatus status = SuccessStatus.SIGNUP_SUCCESS;
        return ResponseEntity.status(status.getHttpStatus())
                .body(BaseResponse.success(status, data));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        TokenResponse data = authService.login(request);
        SuccessStatus status = SuccessStatus.LOGIN_SUCCESS;
        return ResponseEntity.status(status.getHttpStatus())
                .body(BaseResponse.success(status, data));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<TokenResponse>> refresh(@RequestHeader("Authorization") String authorization) {
        String refreshToken = authorization.replace("Bearer ", "");
        TokenResponse data = authService.refresh(refreshToken);
        SuccessStatus status = SuccessStatus.REISSUE_TOKEN_SUCCESS;
        return ResponseEntity.status(status.getHttpStatus())
                .body(BaseResponse.success(status, data));
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(Authentication authentication) {
        if (authentication == null) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        authService.logout(authentication.getName());
        SuccessStatus status = SuccessStatus.LOGOUT_SUCCESS;
        return ResponseEntity.status(status.getHttpStatus())
                .body(BaseResponse.success(status));
    }

    @Override
    @PostMapping("/sms/send")
    public ResponseEntity<BaseResponse<Void>> sendSmsCode(@RequestBody @Valid SmsSendRequest request) {
        authService.sendSmsCode(request);
        SuccessStatus status = SuccessStatus.SMS_SEND_SUCCESS;
        return ResponseEntity.status(status.getHttpStatus())
                .body(BaseResponse.success(status));
    }

    @Override
    @PostMapping("/sms/verify")
    public ResponseEntity<BaseResponse<SmsVerifyResponse>> verifySmsCode(@RequestBody @Valid SmsVerifyRequest request) {
        SmsVerifyResponse data = authService.verifySmsCode(request);
        SuccessStatus status = SuccessStatus.SMS_VERIFY_SUCCESS;
        return ResponseEntity.status(status.getHttpStatus())
                .body(BaseResponse.success(status, data));
    }

    @Override
    @PatchMapping("/password/reset")
    public ResponseEntity<BaseResponse<Void>> resetPassword(
            @RequestBody @Valid PasswordResetRequest request,
            Authentication authentication) {
        String studentNum = authentication != null ? authentication.getName() : null;
        authService.resetPassword(request, studentNum);
        SuccessStatus status = SuccessStatus.PASSWORD_RESET_SUCCESS;
        return ResponseEntity.status(status.getHttpStatus())
                .body(BaseResponse.success(status));
    }
}