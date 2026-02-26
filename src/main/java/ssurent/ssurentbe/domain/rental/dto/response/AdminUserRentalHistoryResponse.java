package ssurent.ssurentbe.domain.rental.dto.response;

import ssurent.ssurentbe.domain.item.entity.Category;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.rental.enums.Status;

import java.time.LocalDateTime;

public record AdminUserRentalHistoryResponse(
        Long rentalId,
        LocalDateTime rentalDate,
        LocalDateTime dueDate,
        LocalDateTime returnDate,
        String itemName,
        Status status,
        boolean isOverdue
) {
    public static AdminUserRentalHistoryResponse from(RentalHistory rentalHistory) {
        Items items = rentalHistory.getItemId();
        return new AdminUserRentalHistoryResponse(
                rentalHistory.getId(),
                rentalHistory.getRentalDate(),
                rentalHistory.getDueDate(),
                rentalHistory.getReturnDate(),
                items.getItemName(),
                rentalHistory.getStatus(),
                rentalHistory.isOverdue()
        );
    }
}
