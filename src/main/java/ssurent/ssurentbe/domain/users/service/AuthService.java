package ssurent.ssurentbe.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.domain.users.dto.LoginRequest;
import ssurent.ssurentbe.domain.users.dto.SignupRequest;
import ssurent.ssurentbe.domain.users.dto.TokenResponse;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;
import ssurent.ssurentbe.domain.users.repository.UserRepository;
import ssurent.ssurentbe.common.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponse signup(SignupRequest request) {
        if (userRepository.existsByStudentNum(request.studentNum())) {
            throw new IllegalArgumentException("이미 가입된 학번입니다.");
        }

        Users user = Users.builder()
                .studentNum(request.studentNum())
                .name(request.name())
                .phoneNum(request.phoneNum())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.NORMAL)
                .status(Status.ACTIVE)
                .isDeleted(false)
                .build();

        userRepository.save(user);

        String accessToken = jwtTokenProvider.createAccessToken(user.getStudentNum());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getStudentNum());

        return TokenResponse.of(accessToken, refreshToken);
    }

    public TokenResponse login(LoginRequest request) {
        Users user = userRepository.findByStudentNum(request.studentNum())
                .orElseThrow(() -> new IllegalArgumentException("학번 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("학번 또는 비밀번호가 일치하지 않습니다.");
        }

        if (user.isDeleted()) {
            throw new IllegalArgumentException("탈퇴한 사용자입니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getStudentNum());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getStudentNum());

        return TokenResponse.of(accessToken, refreshToken);
    }

    public TokenResponse refresh(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        if (!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("리프레시 토큰이 아닙니다.");
        }

        String studentNum = jwtTokenProvider.getStudentNum(refreshToken);
        Users user = userRepository.findByStudentNum(studentNum)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.isDeleted()) {
            throw new IllegalArgumentException("탈퇴한 사용자입니다.");
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(studentNum);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(studentNum);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }
}
