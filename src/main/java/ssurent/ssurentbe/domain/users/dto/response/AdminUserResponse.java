package ssurent.ssurentbe.domain.users.dto.response;

import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;

public record AdminUserResponse(
    Long userId,
    String userName,
    String studentNum,
    Role role
) {
    public static AdminUserResponse from(Users user) {
        return new AdminUserResponse(
                user.getId(),
                user.getName(),
                user.getStudentNum(),
                user.getRole()
        );
    }
}
