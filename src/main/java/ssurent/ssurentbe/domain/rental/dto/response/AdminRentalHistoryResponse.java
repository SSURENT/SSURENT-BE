package ssurent.ssurentbe.domain.rental.dto.response;

import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.rental.enums.Status;

import java.time.LocalDateTime;

// 서비스 로직 단에서 Status = 에 따라서 List 내부에 구성.
// Status = 대여 -> timeStamp = created_At 인 데이터 1개 제공
// Status = 반납 -> timeStamp = created_At(대여) , timeStamp = updated_At(반납)인 데이터 2개 제공
public record AdminRentalHistoryResponse(
        Long rentalId,
        LocalDateTime timeStamp,
        String name,
        String studentNum,
        String phNum,
        Status status,
        String itemName,
        String itemNum,
        boolean isOverdue
) {
}
