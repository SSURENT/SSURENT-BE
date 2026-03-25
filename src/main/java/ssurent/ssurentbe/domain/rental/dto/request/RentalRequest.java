package ssurent.ssurentbe.domain.rental.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ssurent.ssurentbe.domain.assists.entity.Assists;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.rental.enums.Status;
import ssurent.ssurentbe.domain.users.entity.Users;

import java.time.LocalDateTime;

public record RentalRequest(
        @NotNull(message = "물품 ID는 필수입니다.")
        @Schema(description = "대여할 물품 ID", example = "1")
        Long itemId,

        @NotBlank(message = "도우미 이름은 필수입니다.")
        @Schema(description = "담당 도우미 이름", example = "양도영")
        String assistName
) {
    private static final int DEFAULT_RENTAL_DAYS = 3;

    public RentalHistory toEntity(Assists assist, Users user, Items item) {
        LocalDateTime now = LocalDateTime.now();
        return RentalHistory.builder()
                .assistId(assist)
                .userId(user)
                .userInfo(user.getName() + "(" + user.getStudentNum() + ")")
                .itemId(item)
                .rentalDate(now)
                .dueDate(now.plusDays(DEFAULT_RENTAL_DAYS))
                .status(Status.RENT)
                .overdue(false)
                .build();
    }
}
