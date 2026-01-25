package ssurent.ssurentbe.domain.rental.dto.request;

public record RentalReturnRequest(
        String itemId,
        String rentalId,
        String assistName
) {
}
