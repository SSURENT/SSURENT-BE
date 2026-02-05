package ssurent.ssurentbe.domain.rental.dto.request;

public record RentalReturnRequest(
        Long itemId,
        Long rentalId,
        String assistName
) {
}
