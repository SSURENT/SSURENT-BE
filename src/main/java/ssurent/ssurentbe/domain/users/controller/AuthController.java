package ssurent.ssurentbe.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.common.base.BaseResponse;
import ssurent.ssurentbe.common.status.SuccessStatus;
import ssurent.ssurentbe.domain.users.controller.docs.AuthApiDocs;
import ssurent.ssurentbe.domain.users.dto.LoginRequest;
import ssurent.ssurentbe.domain.users.dto.SignupRequest;
import ssurent.ssurentbe.domain.users.dto.TokenResponse;
import ssurent.ssurentbe.domain.users.service.AuthService;

@RestController
@RequestMapping("/api/auth")
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
