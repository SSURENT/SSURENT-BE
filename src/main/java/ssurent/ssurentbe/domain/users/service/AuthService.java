package ssurent.ssurentbe.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.jwt.JwtTokenProvider;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.users.dto.LoginRequest;
import ssurent.ssurentbe.domain.users.dto.SignupRequest;
import ssurent.ssurentbe.domain.users.dto.TokenResponse;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

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

        return TokenResponse.of(accessToken, refreshToken);
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

        return TokenResponse.of(accessToken, refreshToken);
    }

    public TokenResponse refresh(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);
        if (!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new GeneralException(ErrorStatus.JWT_INVALID_TYPE);
        }

        String studentNum = jwtTokenProvider.getStudentNum(refreshToken);
        Users user = userRepository.findByStudentNum(studentNum)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (user.isDeleted()) {
            throw new GeneralException(ErrorStatus.USER_WITHDRAWN);
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(studentNum);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(studentNum);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }
}
