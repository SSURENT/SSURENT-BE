package ssurent.ssurentbe.domain.users.dto.response;

import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;

public record AdminUserResponse(
    Long userId,
    String userName,
    String studentNum,
    Status status,
    Role role
) {
    public static AdminUserResponse from(Users user) {
        return new AdminUserResponse(
                user.getId(),
                user.getName(),
                user.getStudentNum(),
                user.getStatus(),
                user.getRole()
        );
    }
}
