package ssurent.ssurentbe.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdatePhoneNumberRequest(
        @NotBlank(message = "전화번호는 필수 입력값입니다.")
        @Pattern(regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
        String phoneNum
) {
}
