package ssurent.ssurentbe.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.domain.users.entity.UserPanaltyLog;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.repository.UserPanaltyLogRepository;
import ssurent.ssurentbe.domain.users.repository.UserRepository;
import ssurent.ssurentbe.domain.users.dto.*;
import java.util.stream.Collectors;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserPanaltyLogRepository userPanaltyLogRepository;

    public UserInfoResponse getMyInfo(String username) {
        Users user = userRepository.findByStudentNumAndDeletedFalse(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        UserInfoResponse response = UserInfoResponse.from(user);
        return UserInfoResponse.from(user);
    }

    @Transactional
    public void updatePhoneNumber(String username, String phoneNum) {
        Users user = userRepository.findByStudentNumAndDeletedFalse(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.updatePhoneNumber(phoneNum);
    }


}
