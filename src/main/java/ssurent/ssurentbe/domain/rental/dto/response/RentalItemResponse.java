package ssurent.ssurentbe.domain.rental.dto.response;

import ssurent.ssurentbe.domain.item.entity.Category;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;

import java.time.LocalDateTime;

public record RentalItemResponse(
        Long rentalId,
        Long itemId,
        String itemName,
        LocalDateTime dueDate,
        boolean overdue
) {
    public static RentalItemResponse from(RentalHistory rentalHistory) {
        Items items = rentalHistory.getItemId();
        return new RentalItemResponse(
                rentalHistory.getId(),
                rentalHistory.getItemId().getId(),
                items.getItemName(),
                rentalHistory.getDueDate(),
                rentalHistory.isOverdue()
        );
    }
}
