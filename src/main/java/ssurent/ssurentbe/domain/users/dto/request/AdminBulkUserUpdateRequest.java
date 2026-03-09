package ssurent.ssurentbe.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminBulkUserUpdateRequest(
        @NotNull String studentNum,
        @NotBlank String name,
        String phoneNum
) {
}
