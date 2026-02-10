package ssurent.ssurentbe.domain.users.dto.request;

public record LoginRequest(
        String studentNum,
        String password
) {
}
