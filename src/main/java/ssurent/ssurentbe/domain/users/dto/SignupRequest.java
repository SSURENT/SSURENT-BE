package ssurent.ssurentbe.domain.users.dto;

public record SignupRequest(
        String studentNum,
        String name,
        String phoneNum,
        String password
) {
}
