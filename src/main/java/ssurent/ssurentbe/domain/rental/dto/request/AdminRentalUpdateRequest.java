package ssurent.ssurentbe.domain.rental.dto.request;

import java.util.List;

public record AdminRentalUpdateRequest(
        List<RentalUpdate> rentalUpdates
) {
    public record RentalUpdate(
            Long rentalId,
            boolean returned
    ){

    }
}
