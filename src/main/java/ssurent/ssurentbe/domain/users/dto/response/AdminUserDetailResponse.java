package ssurent.ssurentbe.domain.users.dto.response;

import ssurent.ssurentbe.domain.users.entity.UserPenaltyLog;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;

import java.util.ArrayList;
import java.util.List;

public record AdminUserDetailResponse(
        Long userId,
        String userName,
        String studentNum,
        Role role,
        Status status,
        String phoneNum,
        List<UserPenaltyResponse> penalties
) {
    public static AdminUserDetailResponse from(Users user, List<UserPenaltyLog> penalties) {
        return new AdminUserDetailResponse(
                user.getId(),
                user.getName(),
                user.getStudentNum(),
                user.getRole(),
                user.getStatus(),
                user.getPhoneNum(),
                penalties.stream()
                        .map(UserPenaltyResponse::from)
                        .toList()
        );
    }
}
