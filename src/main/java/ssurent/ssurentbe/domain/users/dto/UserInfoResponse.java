package ssurent.ssurentbe.domain.users.dto;

import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;

public record UserInfoResponse(
        String studentNum,
        String name,
        String phoneNum,
        Status status,
        Role role
) {
    public static UserInfoResponse from(Users user) {
        return new UserInfoResponse(
                user.getStudentNum(),
                user.getName(),
                user.getPhoneNum(),
                user.getStatus(),
                user.getRole()
        );
    }
}