package ssurent.ssurentbe.domain.users.dto.request;


import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

    public record AdminUserPenaltyCreateRequest (
            Long userId,
            String itemName,
            @Pattern(regexp = "^(OVERDUE|UNAUTHORIZED_USE|OTHER)$", message = "유효하지 않은 패널티 타입입니다")
            String penaltyType
    ){
    }
