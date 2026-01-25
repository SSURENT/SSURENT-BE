package ssurent.ssurentbe.domain.rental.dto.response;

import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;

import java.time.LocalDateTime;

public record RentalItemResponse(
        String rentalId,
        String itemId,
        String itemName,
        LocalDateTime dueDate
) {
    public static RentalItemResponse from(RentalHistory rentalHistory) {
        Items items = rentalHistory.getItemId();
        String itemName = items.getName() + "(" + items.getItemNum() + ")";
        return new RentalItemResponse(
                rentalHistory.getId(),
                rentalHistory.getItemId().getId(),
                itemName,
                rentalHistory.getDueDate()
        );
    }
}
