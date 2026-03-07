package ssurent.ssurentbe.domain.users.dto.response;

import ssurent.ssurentbe.domain.users.entity.UserPenaltyLog;
import ssurent.ssurentbe.domain.users.enums.PenaltyTypes;

import java.time.LocalDateTime;

public record UserPenaltyResponse(
        Long penaltyId,
        PenaltyTypes penaltyType,
        String itemName,
        LocalDateTime createdAt
) {
    public static UserPenaltyResponse from(UserPenaltyLog log) {
        return new UserPenaltyResponse(
                log.getId(),
                log.getPenaltyType(),
                log.getItemName(),
                log.getCreatedAt()
        );
    }
}
