package ssurent.ssurentbe.common.auth.dto.request;

public record LoginRequest(
        String studentNum,
        String password
) {
}
