package ssurent.ssurentbe.domain.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssurent.ssurentbe.domain.users.dto.LoginRequest;
import ssurent.ssurentbe.domain.users.dto.SignupRequest;
import ssurent.ssurentbe.domain.users.dto.TokenResponse;
import ssurent.ssurentbe.domain.users.service.AuthService;

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "학번, 이름, 전화번호, 비밀번호로 회원가입합니다.")
    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> signup(@RequestBody SignupRequest request) {
        TokenResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그인", description = "학번과 비밀번호로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "토큰 갱신", description = "리프레시 토큰으로 새로운 액세스 토큰을 발급받습니다.")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestHeader("Authorization") String authorization) {
        String refreshToken = authorization.replace("Bearer ", "");
        TokenResponse response = authService.refresh(refreshToken);
        return ResponseEntity.ok(response);
    }
}
