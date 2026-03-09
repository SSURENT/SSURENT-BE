package ssurent.ssurentbe.domain.rental.dto.response;

import ssurent.ssurentbe.domain.rental.entity.RentalReport;
import ssurent.ssurentbe.domain.rental.enums.ProblemType;

import java.time.LocalDateTime;

public record UnresolvedReportResponse(
        Long reportId,
        Long rentalId,
        String itemName,
        ProblemType problemType,
        String description,
        LocalDateTime reportedAt
) {
    public static UnresolvedReportResponse from(RentalReport report) {
        return new UnresolvedReportResponse(
                report.getId(),
                report.getRentalHistory().getId(),
                report.getRentalHistory().getItemId().getItemName(),
                report.getProblemType(),
                report.getDescription(),
                report.getReportedAt()
        );
    }
}