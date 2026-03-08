package ssurent.ssurentbe.domain.rental.dto.request;

import jakarta.validation.constraints.NotNull;
import ssurent.ssurentbe.domain.rental.enums.ProblemType;

public record RentalReportRequest(
        @NotNull Long rentalId,
        @NotNull ProblemType problemType,
        String description
) {
}
