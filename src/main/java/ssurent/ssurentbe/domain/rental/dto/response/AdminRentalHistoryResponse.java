package ssurent.ssurentbe.domain.rental.dto.response;

import ssurent.ssurentbe.domain.rental.enums.Status;

import java.time.LocalDateTime;

public record AdminRentalHistoryResponse(
        LocalDateTime timeStamp,
        String name,
        String studentNum,
        String phNum,
        Status status,
        String itemName,
        String itemNum,
        boolean isOverdue
) {
}
