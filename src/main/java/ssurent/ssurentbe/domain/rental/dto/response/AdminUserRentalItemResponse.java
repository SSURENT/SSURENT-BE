package ssurent.ssurentbe.domain.rental.dto.response;

import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;

import java.time.LocalDateTime;

public record AdminUserRentalItemResponse(
        Long rentalId,
        LocalDateTime rentalDate,
        LocalDateTime returnDate,
        String itemName
) {
    public static AdminUserRentalItemResponse from(RentalHistory rentalHistory) {
        Items items = rentalHistory.getItemId();
        String itemName = items.getName() + "(" + items.getItemNum() + ")";
        return new AdminUserRentalItemResponse(
                rentalHistory.getId(),
                rentalHistory.getRentalDate(),
                rentalHistory.getReturnDate(),
                itemName
        );
    }
}
