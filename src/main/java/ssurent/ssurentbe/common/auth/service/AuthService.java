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
import ssurent.ssurentbe.common.auth.dto.request.PasswordResetRequest;
import ssurent.ssurentbe.common.auth.dto.request.SignupRequest;
import ssurent.ssurentbe.common.auth.dto.request.SmsSendRequest;
import ssurent.ssurentbe.common.auth.dto.request.SmsVerifyRequest;
import ssurent.ssurentbe.common.auth.dto.response.SmsVerifyResponse;
import ssurent.ssurentbe.common.auth.dto.response.TokenResponse;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final SmsService smsService;

    @Value("${jwt.refresh-token-validity}")
    private long refreshTokenValidity;

    private static final String REFRESH_TOKEN_PREFIX = "RT:";
    private static final String SMS_CODE_PREFIX = "SMS:CODE:";
    private static final String SMS_RESET_PREFIX = "SMS:RESET:";
    private static final String SMS_FAIL_PREFIX = "SMS:FAIL:";
    private static final long SMS_CODE_TTL_MINUTES = 5L;
    private static final long SMS_RESET_TTL_MINUTES = 10L;
    private static final int SMS_MAX_ATTEMPTS = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

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

    @Transactional
    public void sendSmsCode(SmsSendRequest request) {
        Users user = userRepository.findByStudentNumAndDeletedFalse(request.studentNum())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (!request.phoneNum().replace("-", "").equals(user.getPhoneNum().replace("-", ""))) {
            throw new GeneralException(ErrorStatus.PHONE_NUM_MISMATCH);
        }

        String normalizedPhone = request.phoneNum().replace("-", "");
        String code = String.format("%06d", RANDOM.nextInt(1_000_000));
        redisTemplate.opsForValue().set(
                SMS_CODE_PREFIX + normalizedPhone,
                code,
                SMS_CODE_TTL_MINUTES,
                TimeUnit.MINUTES
        );

        smsService.sendVerificationCode(normalizedPhone, code);
    }

    public SmsVerifyResponse verifySmsCode(SmsVerifyRequest request) {
        String normalizedPhone = request.phoneNum().replace("-", "");
        String storedCode = redisTemplate.opsForValue().get(SMS_CODE_PREFIX + normalizedPhone);

        if (storedCode == null) {
            throw new GeneralException(ErrorStatus.VERIFICATION_CODE_EXPIRED);
        }

        String failKey = SMS_FAIL_PREFIX + normalizedPhone;
        String failCountStr = redisTemplate.opsForValue().get(failKey);
        if (failCountStr != null && Integer.parseInt(failCountStr) >= SMS_MAX_ATTEMPTS) {
            redisTemplate.delete(SMS_CODE_PREFIX + normalizedPhone);
            redisTemplate.delete(failKey);
            throw new GeneralException(ErrorStatus.VERIFICATION_ATTEMPT_EXCEEDED);
        }

        if (!storedCode.equals(request.code())) {
            Long failCount = redisTemplate.opsForValue().increment(failKey);
            if (failCount == 1) {
                redisTemplate.expire(failKey, SMS_CODE_TTL_MINUTES, TimeUnit.MINUTES);
            }
            if (failCount >= SMS_MAX_ATTEMPTS) {
                redisTemplate.delete(SMS_CODE_PREFIX + normalizedPhone);
                redisTemplate.delete(failKey);
                throw new GeneralException(ErrorStatus.VERIFICATION_ATTEMPT_EXCEEDED);
            }
            throw new GeneralException(ErrorStatus.INVALID_VERIFICATION_CODE);
        }

        redisTemplate.delete(SMS_CODE_PREFIX + normalizedPhone);
        redisTemplate.delete(failKey);

        Users user = userRepository.findByNormalizedPhoneNumAndDeletedFalse(normalizedPhone)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        String resetToken = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(
                SMS_RESET_PREFIX + resetToken,
                user.getStudentNum(),
                SMS_RESET_TTL_MINUTES,
                TimeUnit.MINUTES
        );

        return new SmsVerifyResponse(resetToken);
    }

    @Transactional
    public void resetPassword(PasswordResetRequest request, String studentNum) {
        Users user;

        if (studentNum != null) {
            user = userRepository.findByStudentNumAndDeletedFalse(studentNum)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        } else {
            if (request.resetToken() == null) {
                throw new GeneralException(ErrorStatus.INVALID_RESET_TOKEN);
            }
            String resetStudentNum = redisTemplate.opsForValue().get(SMS_RESET_PREFIX + request.resetToken());
            if (resetStudentNum == null) {
                throw new GeneralException(ErrorStatus.INVALID_RESET_TOKEN);
            }
            user = userRepository.findByStudentNumAndDeletedFalse(resetStudentNum)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
            redisTemplate.delete(SMS_RESET_PREFIX + request.resetToken());
        }

        user.updatePassword(passwordEncoder.encode(request.newPassword()));
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + user.getStudentNum());
    }
}
