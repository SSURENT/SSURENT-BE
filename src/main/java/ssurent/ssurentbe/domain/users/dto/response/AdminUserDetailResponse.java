package ssurent.ssurentbe.domain.users.dto.response;

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
    public static AdminUserDetailResponse from(Users user) {
        // TODO : 이후 Users 가 Penalty를 관리하게 해서 추가시켜주도록
        List<UserPenaltyResponse> penaltyList = new ArrayList<>();

        return new AdminUserDetailResponse(
                user.getId(),
                user.getName(),
                user.getStudentNum(),
                user.getRole().name(),
                user.getStatus().name(),
                user.getPhoneNum(),
                penaltyList
        );
    }
}
