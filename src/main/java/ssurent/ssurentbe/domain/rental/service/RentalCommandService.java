package ssurent.ssurentbe.domain.rental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.assists.entity.Assists;
import ssurent.ssurentbe.domain.assists.repository.AssistsRepository;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.item.enums.Condition;
import ssurent.ssurentbe.domain.item.enums.Status;
import ssurent.ssurentbe.domain.item.repository.ItemRepository;
import ssurent.ssurentbe.domain.rental.dto.request.RentalExtendRequest;
import ssurent.ssurentbe.domain.rental.dto.request.RentalReportCheckRequest;
import ssurent.ssurentbe.domain.rental.dto.request.RentalReportRequest;
import ssurent.ssurentbe.domain.rental.dto.request.RentalRequest;
import ssurent.ssurentbe.domain.rental.dto.request.RentalReturnRequest;
import ssurent.ssurentbe.domain.rental.dto.response.RentalItemResponse;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.rental.entity.RentalReport;
import ssurent.ssurentbe.domain.rental.enums.ProblemType;
import ssurent.ssurentbe.domain.rental.repository.RentalReportRepository;
import ssurent.ssurentbe.domain.rental.repository.RentalRepository;
import static ssurent.ssurentbe.domain.rental.enums.Status.RENT;
import static ssurent.ssurentbe.domain.rental.enums.Status.RETURN;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalCommandService {

    private final ItemRepository itemRepository;
    private final AssistsRepository assistsRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final RentalReportRepository rentalReportRepository;

    @Transactional
    public RentalItemResponse createRental(String studentNum, RentalRequest request) {
        Users user = userRepository.findByStudentNumAndDeletedFalse(studentNum)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Items item = itemRepository.findByIdWithLock(request.itemId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.ITEM_NOT_FOUND));

        if (item.isDeleted()) {
            throw new GeneralException(ErrorStatus.ITEM_NOT_FOUND);
        }

        if (item.getStatus() != Status.ACTIVE) {
            throw new GeneralException(ErrorStatus.ITEM_NOT_AVAILABLE);
        }

        if (rentalRepository.existsByItemId_IdAndStatus(item.getId(), RENT)) {
            throw new GeneralException(ErrorStatus.ITEM_NOT_AVAILABLE);
        }

        Assists assist = assistsRepository.findByNameAndDeletedFalse(request.assistName())
                .orElseThrow(() -> new GeneralException(ErrorStatus.ASSIST_NOT_FOUND));

        RentalHistory rentalHistory = request.toEntity(assist, user, item);

        rentalRepository.save(rentalHistory);
        item.updateStatus(Status.INACTIVE);
        item.updateCondition(Condition.RENT);

        return RentalItemResponse.from(rentalHistory);
    }

    @Transactional
    public void extendRental(String studentNum, RentalExtendRequest request) {
        Users user = userRepository.findByStudentNumAndDeletedFalse(studentNum)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        RentalHistory rentalHistory = rentalRepository.findById(request.rentalId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.RENTAL_HISTORY_NOT_FOUND));

        if (!rentalHistory.getUserId().getId().equals(user.getId())) {
            throw new GeneralException(ErrorStatus.FORBIDDEN);
        }

        if (rentalHistory.getStatus() == RETURN) {
            throw new GeneralException(ErrorStatus.RENTAL_ALREADY_RETURNED);
        }

        if (rentalHistory.isPostponed()) {
            throw new GeneralException(ErrorStatus.RENTAL_ALREADY_EXTENDED);
        }

        rentalHistory.extend();
    }

    @Transactional
    public void returnRental(String studentNum, RentalReturnRequest request) {
        Users user = userRepository.findByStudentNumAndDeletedFalse(studentNum)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        RentalHistory rentalHistory = rentalRepository.findById(request.rentalId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.RENTAL_HISTORY_NOT_FOUND));

        if (!rentalHistory.getUserId().getId().equals(user.getId())) {
            throw new GeneralException(ErrorStatus.FORBIDDEN);
        }

        if (rentalHistory.getStatus() == RETURN) {
            throw new GeneralException(ErrorStatus.RENTAL_ALREADY_RETURNED);
        }

        rentalHistory.returnRental();

        Items item = rentalHistory.getItemId();
        item.updateStatus(Status.ACTIVE);
        item.updateCondition(Condition.KEEP);
    }

    @Transactional
    public void reportRental(String studentNum, RentalReportRequest request) {
        Users user = userRepository.findByStudentNumAndDeletedFalse(studentNum)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        RentalHistory rentalHistory = rentalRepository.findById(request.rentalId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.RENTAL_HISTORY_NOT_FOUND));

        if (!rentalHistory.getUserId().getId().equals(user.getId())) {
            throw new GeneralException(ErrorStatus.FORBIDDEN);
        }

        if (rentalHistory.getStatus() == RETURN) {
            throw new GeneralException(ErrorStatus.RENTAL_ALREADY_RETURNED);
        }

        if (request.problemType() == ProblemType.OTHER &&
                (request.description() == null || request.description().isBlank())) {
            throw new GeneralException(ErrorStatus.RENTAL_REPORT_DESCRIPTION_REQUIRED);
        }

        rentalReportRepository.save(RentalReport.of(rentalHistory, request.problemType(), request.description()));
    }

    @Transactional
    public void checkReports(RentalReportCheckRequest request) {
        List<RentalReport> reports = rentalReportRepository.findAllById(request.reportIds());

        if (reports.size() != request.reportIds().size()) {
            throw new GeneralException(ErrorStatus.RENTAL_REPORT_NOT_FOUND);
        }

        reports.forEach(RentalReport::resolve);
    }
}