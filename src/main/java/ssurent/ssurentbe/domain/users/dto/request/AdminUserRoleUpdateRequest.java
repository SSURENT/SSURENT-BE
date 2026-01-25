package ssurent.ssurentbe.domain.users.dto.request;

public record AdminUserRoleUpdateRequest(
        String userId,
        String role
) {
}
