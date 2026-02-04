package ssurent.ssurentbe.domain.users.dto.response;

import ssurent.ssurentbe.domain.users.entity.UserPenaltyLog;
import ssurent.ssurentbe.domain.users.entity.Users;

import java.util.ArrayList;
import java.util.List;

public record AdminUserDetailResponse(
        String userId,
        String userName,
        String studentNum,
        String role,
        String status,
        String phoneNum,
        List<UserPenaltyResponse> penalties
) {
    public static AdminUserDetailResponse from(Users user, List<UserPenaltyLog> penalties) {
        return new AdminUserDetailResponse(
                user.getId(),
                user.getName(),
                user.getStudentNum(),
                user.getRole().name(),
                user.getStatus().name(),
                user.getPhoneNum(),
                penalties.stream()
                        .map(UserPenaltyResponse::from)
                        .toList()
        );
    }
}
