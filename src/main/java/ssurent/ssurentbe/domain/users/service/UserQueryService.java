package ssurent.ssurentbe.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.users.dto.response.AdminUserDetailResponse;
import ssurent.ssurentbe.domain.users.dto.response.AdminUserResponse;
import ssurent.ssurentbe.domain.users.dto.response.UserInfoResponse;
import ssurent.ssurentbe.domain.users.dto.response.UserPenaltyResponse;
import ssurent.ssurentbe.domain.users.entity.UserPenaltyLog;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;
import ssurent.ssurentbe.domain.users.repository.UserPenaltyLogRepository;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {
    private final UserRepository userRepository;
    private final UserPenaltyLogRepository userPenaltyLogRepository;

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

        List<UserPenaltyLog> logs = userPenaltyLogRepository.findByUserIdOrderByCreatedAtDesc(user);

        return logs.stream()
                .map(UserPenaltyResponse::from)
                .collect(Collectors.toList());
    }

    public List<AdminUserResponse> getUsersByStatus(String status) {
        List<Users> users = switch (status) {
            case "전체회원" -> userRepository.findByDeletedFalse();
            case "정지회원" -> userRepository.findByStatusAndDeletedFalse(Status.BANNED);
            case "관리자"   -> userRepository.findByRoleInAndDeletedFalse(List.of(Role.ADMIN, Role.SUPERADMIN));
            default -> throw new IllegalArgumentException("지원하지 않는 상태입니다: " + status);
        };

        return users.stream()
                .map(AdminUserResponse::from)
                .toList();
    }

    public AdminUserDetailResponse getUserDetails(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        List<UserPenaltyLog> logs = userPenaltyLogRepository.findByUserIdOrderByCreatedAtDesc(user);

        return AdminUserDetailResponse.from(user,logs);
    }
}
