package ssurent.ssurentbe.common.auth.dto.request;

public record SmsVerifyRequest(String phoneNum, String code) {
}
