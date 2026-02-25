package ssurent.ssurentbe.domain.rental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.rental.dto.response.AdminUserRentalHistoryResponse;
import ssurent.ssurentbe.domain.rental.dto.response.RentalItemResponse;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.rental.enums.Status;
import ssurent.ssurentbe.domain.rental.repository.RentalRepository;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalQueryService {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public List<RentalItemResponse> getMyRentals(String studentNum) {
        Users user = userRepository.findByStudentNumAndDeletedFalse(studentNum)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        List<RentalHistory> histories = rentalRepository.findActiveRentalsByUserId(user.getId(), Status.RENT);

        if (histories.isEmpty()) {
            throw new GeneralException(ErrorStatus.RENTAL_HISTORY_NOT_FOUND);
        }

        return histories.stream()
                .map(RentalItemResponse::from)
                .toList();
    }

    public List<AdminUserRentalHistoryResponse> getUserRentalHistory(
            Long userId,
            LocalDate startDate,
            LocalDate endDate,
            String itemName
    ) {
        if (!userRepository.existsById(userId)) {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new GeneralException(ErrorStatus.RENTAL_INVALID_DATE_RANGE);
        }

        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        List<RentalHistory> histories = rentalRepository.findByUserIdAndFilters(
                userId,
                startDateTime,
                endDateTime,
                itemName
        );

        if (histories.isEmpty()) {
            throw new GeneralException(ErrorStatus.RENTAL_HISTORY_NOT_FOUND);
        }

        return histories.stream()
                .map(AdminUserRentalHistoryResponse::from)
                .toList();
    }
}