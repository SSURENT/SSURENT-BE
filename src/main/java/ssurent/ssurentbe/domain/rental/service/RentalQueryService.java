package ssurent.ssurentbe.domain.rental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.item.repository.CategoryRepository;
import ssurent.ssurentbe.domain.rental.dto.response.AdminCategoryRentalStatisticsResponse;
import ssurent.ssurentbe.domain.rental.dto.response.AdminItemRentalStatisticsResponse;
import ssurent.ssurentbe.domain.rental.dto.response.AdminPeriodRentalStatisticsResponse;
import ssurent.ssurentbe.domain.rental.dto.response.AdminUserRentalHistoryResponse;
import ssurent.ssurentbe.domain.rental.dto.response.RentalItemResponse;
import ssurent.ssurentbe.domain.rental.dto.response.UnresolvedReportCountResponse;
import ssurent.ssurentbe.domain.rental.dto.response.UnresolvedReportResponse;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.rental.enums.Status;
import ssurent.ssurentbe.domain.rental.repository.RentalReportRepository;
import ssurent.ssurentbe.domain.rental.repository.RentalRepository;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalQueryService {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final CategoryRepository categoryRepository;
    private final RentalReportRepository rentalReportRepository;

    public List<UnresolvedReportResponse> getUnresolvedReports() {
        return rentalReportRepository.findAllByResolvedFalse().stream()
                .map(UnresolvedReportResponse::from)
                .toList();
    }

    public UnresolvedReportCountResponse getUnresolvedReportCount() {
        return new UnresolvedReportCountResponse(rentalReportRepository.countByResolvedFalse());
    }

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

    public List<?> getRentalItemStatistics(
            String categoryId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (startDate == null || endDate == null){
            throw new GeneralException(ErrorStatus.RENTAL_INVALID_DATE_RANGE);
        }

        if (startDate.isAfter(endDate)) {
            throw new GeneralException(ErrorStatus.RENTAL_INVALID_DATE_RANGE);
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        if ("ALL".equalsIgnoreCase(categoryId)) {
            List<Object[]> results = rentalRepository.countByCategoryAndDateRange(startDateTime, endDateTime);
            return results.stream()
                    .map(row -> new AdminCategoryRentalStatisticsResponse(
                            (Long) row[0],
                            (String) row[1],
                            (Long) row[2]
                    ))
                    .toList();
        }

        Long categoryIdLong;
        try {
            categoryIdLong = Long.parseLong(categoryId);
        } catch (NumberFormatException e) {
            throw new GeneralException(ErrorStatus.RENTAL_INVALID_CATEGORY_ID);
        }

        if (!categoryRepository.existsById(categoryIdLong)) {
            throw new GeneralException(ErrorStatus.CATEGORY_NOT_FOUND);
        }

        List<Object[]> results = rentalRepository.countByItemAndDateRange(categoryIdLong, startDateTime, endDateTime);
        return results.stream()
                .map(row -> new AdminItemRentalStatisticsResponse(
                        (Long) row[0],
                        (String) row[1],
                        (String) row[2],
                        (Long) row[3]
                ))
                .toList();
    }

    public List<AdminPeriodRentalStatisticsResponse> getRentalPeriodStatistics(
            String categoryId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (startDate == null || endDate == null) {
                        throw new GeneralException(ErrorStatus.RENTAL_INVALID_DATE_RANGE);
        }
        if (startDate.isAfter(endDate)) {
            throw new GeneralException(ErrorStatus.RENTAL_INVALID_DATE_RANGE);
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Object[]> results;
        if ("ALL".equalsIgnoreCase(categoryId)) {
            results = rentalRepository.countByMonthAndDateRange(startDateTime, endDateTime);
        } else {
            Long categoryIdLong;
            try {
                categoryIdLong = Long.parseLong(categoryId);
            } catch (NumberFormatException e) {
                throw new GeneralException(ErrorStatus.RENTAL_INVALID_CATEGORY_ID);
            }

            if (!categoryRepository.existsById(categoryIdLong)) {
                throw new GeneralException(ErrorStatus.CATEGORY_NOT_FOUND);
            }

            results = rentalRepository.countByCategoryAndMonthAndDateRange(categoryIdLong, startDateTime, endDateTime);
        }

        Map<YearMonth, Long> countByMonth = results.stream()
                .collect(Collectors.toMap(
                        row -> YearMonth.of(((Number) row[0]).intValue(), ((Number) row[1]).intValue()),
                        row -> ((Number) row[2]).longValue()
                ));

        List<AdminPeriodRentalStatisticsResponse> response = new ArrayList<>();
        YearMonth current = YearMonth.from(startDate);
        YearMonth end = YearMonth.from(endDate);

        while (!current.isAfter(end)) {
            response.add(new AdminPeriodRentalStatisticsResponse(
                    current.getYear(),
                    current.getMonthValue(),
                    countByMonth.getOrDefault(current, 0L)
            ));
            current = current.plusMonths(1);
        }

        return response;
    }
}