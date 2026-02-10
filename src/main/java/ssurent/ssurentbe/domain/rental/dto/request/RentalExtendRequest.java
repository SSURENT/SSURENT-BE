package ssurent.ssurentbe.domain.rental.dto.request;

import java.time.LocalDateTime;

public record RentalExtendRequest(
        Long rentalId,
        LocalDateTime dueDate
) {
}
