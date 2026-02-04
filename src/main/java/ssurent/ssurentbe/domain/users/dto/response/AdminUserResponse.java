package ssurent.ssurentbe.domain.users.dto.response;

import ssurent.ssurentbe.domain.users.enums.Role;

public record AdminUserResponse(
    Long userId,
    String userName,
    String studentNum,
    Role role
) {
}
