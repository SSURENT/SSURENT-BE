package ssurent.ssurentbe.domain.rental.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RentalReportCheckRequest(
        @NotNull @NotEmpty List<Long> reportIds
) {
}