package ssurent.ssurentbe.domain.rental.dto.response;

import java.time.LocalDateTime;

public record AdminUserRentalItemResponse(
        Long RentalId,
        LocalDateTime rentalDate,
        LocalDateTime returnDate,
        String itemName
) {
}
