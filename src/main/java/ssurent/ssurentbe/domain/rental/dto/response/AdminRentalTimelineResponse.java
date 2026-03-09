package ssurent.ssurentbe.domain.rental.dto.response;

import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.rental.enums.RentalEventType;
import ssurent.ssurentbe.domain.rental.enums.RentalItemCondition;
import ssurent.ssurentbe.domain.rental.enums.Status;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.item.entity.Items;

import java.time.LocalDateTime;

public record AdminRentalTimelineResponse(
        String userName,
        String studentNum,
        String phoneNum,
        RentalEventType eventType,
        String categoryName,
        String itemName,
        LocalDateTime eventTime,
        String itemNum,
        RentalItemCondition itemCondition
) {
    public static AdminRentalTimelineResponse ofRent(RentalHistory rh, RentalItemCondition condition) {
        Users user = rh.getUserId();
        Items item = rh.getItemId();
        return new AdminRentalTimelineResponse(
                user.getName(),
                user.getStudentNum(),
                user.getPhoneNum(),
                RentalEventType.RENT,
                item.getCategoryId().getName(),
                item.getItemName(),
                rh.getRentalDate(),
                item.getItemNum(),
                condition
        );
    }

    public static AdminRentalTimelineResponse ofReturn(RentalHistory rh, RentalItemCondition condition) {
        Users user = rh.getUserId();
        Items item = rh.getItemId();
        return new AdminRentalTimelineResponse(
                user.getName(),
                user.getStudentNum(),
                user.getPhoneNum(),
                RentalEventType.RETURN,
                item.getCategoryId().getName(),
                item.getItemName(),
                rh.getReturnDate(),
                item.getItemNum(),
                condition
        );
    }

    public static RentalItemCondition resolveCondition(RentalHistory rh) {
        if (rh.getStatus() == Status.RENT) {
            return RentalItemCondition.UNRETURNED;
        }
        return rh.isOverdue() ? RentalItemCondition.OVERDUE : RentalItemCondition.NORMAL;
    }
}
