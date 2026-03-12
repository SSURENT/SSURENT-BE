package ssurent.ssurentbe.domain.rental.dto.request;

import jakarta.validation.constraints.NotNull;

public record AdminRentalReturnRequest(
        @NotNull Long rentalId
) {
}
