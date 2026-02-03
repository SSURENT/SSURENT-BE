package ssurent.ssurentbe.domain.users.dto;

public record LoginRequest(
        String studentNum,
        String password
) {
}
