package ssurent.ssurentbe.domain.rental.dto.request;

import ssurent.ssurentbe.domain.assists.entity.Assists;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.rental.enums.Status;
import ssurent.ssurentbe.domain.users.entity.Users;

import java.time.LocalDateTime;

public record RentalRequest(
        Long itemId,
        String assistName
) {
    private static final int DEFAULT_RENTAL_DAYS = 7;

    public RentalHistory toEntity(Assists assist, Users user, Items item) {
        LocalDateTime now = LocalDateTime.now();
        return RentalHistory.builder()
                .assistId(assist)
                .userId(user)
                .itemId(item)
                .rentalDate(now)
                .dueDate(now.plusDays(DEFAULT_RENTAL_DAYS))
                .status(Status.RENT)
                .overdue(false)
                .build();
    }
}
