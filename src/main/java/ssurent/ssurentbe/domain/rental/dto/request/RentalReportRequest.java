package ssurent.ssurentbe.domain.rental.dto.request;

public record RentalReportRequest(
        Long rentalId,
        String problem
) {
}
