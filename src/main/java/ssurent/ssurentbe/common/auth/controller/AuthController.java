package ssurent.ssurentbe.common.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.common.auth.controller.docs.AuthApiDocs;
import ssurent.ssurentbe.common.auth.service.AuthService;
import ssurent.ssurentbe.common.auth.dto.request.LoginRequest;
import ssurent.ssurentbe.common.auth.dto.request.SignupRequest;
import ssurent.ssurentbe.common.auth.dto.response.TokenResponse;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApiDocs {

    private final AuthService authService;

    @Override
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<TokenResponse>> signup(@RequestBody SignupRequest request) {
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
}
