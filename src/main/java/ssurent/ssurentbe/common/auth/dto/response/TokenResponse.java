package ssurent.ssurentbe.common.auth.dto.response;

import ssurent.ssurentbe.domain.users.enums.Role;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Role role
) {
    public static TokenResponse of(String accessToken, String refreshToken, Role role) {
        return new TokenResponse(accessToken, refreshToken, "Bearer", role);
    }
}