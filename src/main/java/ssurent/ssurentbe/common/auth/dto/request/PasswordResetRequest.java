package ssurent.ssurentbe.common.auth.dto.request;

public record PasswordResetRequest(String resetToken, String newPassword) {
}
