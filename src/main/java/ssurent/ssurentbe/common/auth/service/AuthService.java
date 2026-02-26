package ssurent.ssurentbe.common.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.jwt.JwtTokenProvider;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.common.auth.dto.request.LoginRequest;
import ssurent.ssurentbe.common.auth.dto.request.SignupRequest;
import ssurent.ssurentbe.common.auth.dto.response.TokenResponse;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.refresh-token-validity}")
    private long refreshTokenValidity;

    private static final String REFRESH_TOKEN_PREFIX = "RT:";

    @Transactional
    public TokenResponse signup(SignupRequest request) {
        if (userRepository.existsByStudentNum(request.studentNum())) {
            throw new GeneralException(ErrorStatus.DUPLICATE_STUDENT_NUM);
        }

        Users user = Users.builder()
                .studentNum(request.studentNum())
                .name(request.name())
                .phoneNum(request.phoneNum())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.NORMAL)
                .status(Status.ACTIVE)
                .deleted(false)
                .build();

        userRepository.save(user);

        String accessToken = jwtTokenProvider.createAccessToken(user.getStudentNum());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getStudentNum());

        redisTemplate.opsForValue().set(
                REFRESH_TOKEN_PREFIX + user.getStudentNum(),
                refreshToken,
                refreshTokenValidity,
                TimeUnit.MILLISECONDS
        );

        return TokenResponse.of(accessToken, refreshToken, user.getRole());
    }

    public TokenResponse login(LoginRequest request) {
        Users user = userRepository.findByStudentNum(request.studentNum())
                .orElseThrow(() -> new GeneralException(ErrorStatus.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.INVALID_CREDENTIALS);
        }

        if (user.isDeleted()) {
            throw new GeneralException(ErrorStatus.USER_WITHDRAWN);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getStudentNum());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getStudentNum());

        redisTemplate.opsForValue().set(
                REFRESH_TOKEN_PREFIX + user.getStudentNum(),
                refreshToken,
                refreshTokenValidity,
                TimeUnit.MILLISECONDS
        );

        return TokenResponse.of(accessToken, refreshToken, user.getRole());
    }

    public TokenResponse refresh(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);
        if (!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new GeneralException(ErrorStatus.JWT_INVALID_TYPE);
        }

        String studentNum = jwtTokenProvider.getStudentNum(refreshToken);

        String storedToken = redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + studentNum);
        if (storedToken == null) {
            throw new GeneralException(ErrorStatus.REFRESH_TOKEN_NOT_FOUND);
        }
        if (!storedToken.equals(refreshToken)) {
            throw new GeneralException(ErrorStatus.REFRESH_TOKEN_MISMATCH);
        }

        Users user = userRepository.findByStudentNum(studentNum)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (user.isDeleted()) {
            throw new GeneralException(ErrorStatus.USER_WITHDRAWN);
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(studentNum);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(studentNum);

        redisTemplate.opsForValue().set(
                REFRESH_TOKEN_PREFIX + studentNum,
                newRefreshToken,
                refreshTokenValidity,
                TimeUnit.MILLISECONDS
        );

        return TokenResponse.of(newAccessToken, newRefreshToken, user.getRole());
    }

    public void logout(String studentNum) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + studentNum);
    }
}
