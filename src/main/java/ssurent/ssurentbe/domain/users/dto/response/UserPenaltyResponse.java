package ssurent.ssurentbe.domain.users.dto.response;

import ssurent.ssurentbe.domain.users.entity.UserPenaltyLog;
import ssurent.ssurentbe.domain.users.enums.PenaltyTypes;

import java.time.LocalDateTime;

public record UserPenaltyResponse(
        Long penaltyId,
        PenaltyTypes penaltyType,
        Long itemId,
        String itemName,
        Long rentalHistoryId,
        LocalDateTime createdAt
) {
    public static UserPenaltyResponse from(UserPenaltyLog log) {
        return new UserPenaltyResponse(
                log.getId(),
                log.getPenaltyType(),
                log.getItemsId() != null ? log.getItemsId().getId() : null,
                log.getItemsId() != null ? log.getItemsId().getName() : null,
                log.getRentalHistoryId() != null ? log.getRentalHistoryId().getId() : null,
                log.getCreatedAt()
        );
    }
}
