package ssurent.ssurentbe.domain.users.dto;

import ssurent.ssurentbe.domain.users.entity.UserPanaltyLog;
import ssurent.ssurentbe.domain.users.enums.PanaltyTypes;

import java.time.LocalDateTime;

public record UserPenaltyResponse(
        Long penaltyId,
        PanaltyTypes penaltyType,
        Long itemId,
        Long rentalHistoryId,
        LocalDateTime createdAt
) {
    public static UserPenaltyResponse from(UserPanaltyLog log) {
        return new UserPenaltyResponse(
                log.getId(),
                log.getPanaltyType(),
                log.getItemsId() != null ? log.getItemsId().getId() : null,
                log.getRentalHistoryId() != null ? log.getRentalHistoryId().getId() : null,
                log.getCreatedAt()
        );
    }
}
