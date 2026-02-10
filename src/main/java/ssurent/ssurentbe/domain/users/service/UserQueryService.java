package ssurent.ssurentbe.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.users.dto.response.UserInfoResponse;
import ssurent.ssurentbe.domain.users.dto.response.UserPenaltyResponse;
import ssurent.ssurentbe.domain.users.entity.UserPanaltyLog;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.repository.UserPanaltyLogRepository;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {
    private final UserRepository userRepository;
    private final UserPanaltyLogRepository userPanaltyLogRepository;

    public Users getUserInfo(String username) {
        return userRepository.findByStudentNumAndDeletedFalse(username)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }
    public UserInfoResponse getMyInfo(String username) {
        Users user = getUserInfo(username);
        return UserInfoResponse.from(user);
    }
    public List<UserPenaltyResponse> getMyPenalties(String username) {
        Users user = getUserInfo(username);

        List<UserPanaltyLog> logs = userPanaltyLogRepository.findByUserIdOrderByCreatedAtDesc(user);

        return logs.stream()
                .map(UserPenaltyResponse::from)
                .collect(Collectors.toList());
    }
}
