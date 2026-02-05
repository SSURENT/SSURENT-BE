package ssurent.ssurentbe.domain.users.dto.response;

import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;

public record UserResponse(
        Long userId,
        String studentNum,
        String name,
        Role role,
        Status status,
        String phoneNum
) {
    public static UserResponse from(Users user) {
        return new UserResponse(
                user.getId(),
                user.getStudentNum(),
                user.getName(),
                user.getRole(),
                user.getStatus(),
                user.getPhoneNum()
        );
    }
}
