package ssurent.ssurentbe.domain.users.dto.request;

import ssurent.ssurentbe.domain.users.enums.Role;

public record AdminUserRoleUpdateRequest(
        Long userId,
        Role role
) {
}
