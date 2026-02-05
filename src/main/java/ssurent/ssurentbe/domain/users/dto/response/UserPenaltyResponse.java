package ssurent.ssurentbe.domain.users.dto.response;

import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.users.entity.UserPenaltyLog;

import java.time.LocalDateTime;

public record UserPenaltyResponse(
        Long userPenaltyId,
        LocalDateTime createdAt,
        String itemName,
        String penaltyType
) {
    public static UserPenaltyResponse from(UserPenaltyLog userPenaltyLog) {
        Items item = userPenaltyLog.getItemsId();
        String itemInfo = item.getName() + " (" + item.getItemNum() + ")";

        return new UserPenaltyResponse(
                userPenaltyLog.getId(),
                userPenaltyLog.getCreatedAt(),
                itemInfo,
                userPenaltyLog.getPenaltyType().getDescription()
        );
    }
}
