package ssurent.ssurentbe.domain.rental.dto.request;

import java.time.LocalDateTime;

public record RentalExtendRequest(
        String rentalId,
        LocalDateTime dueDate
) {
}
