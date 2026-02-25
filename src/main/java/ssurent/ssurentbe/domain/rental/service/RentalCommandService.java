package ssurent.ssurentbe.domain.rental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.assists.entity.Assists;
import ssurent.ssurentbe.domain.assists.repository.AssistsRepository;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.item.enums.Status;
import ssurent.ssurentbe.domain.item.repository.ItemRepository;
import ssurent.ssurentbe.domain.rental.dto.request.RentalRequest;
import ssurent.ssurentbe.domain.rental.dto.response.RentalItemResponse;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.rental.repository.RentalRepository;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RentalCommandService {

    private final ItemRepository itemRepository;
    private final AssistsRepository assistsRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Transactional
    public RentalItemResponse createRental(String studentNum, RentalRequest request) {
        Users user = userRepository.findByStudentNumAndDeletedFalse(studentNum)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Items item = itemRepository.findById(request.itemId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.ITEM_NOT_FOUND));

        if (item.isDeleted()) {
            throw new GeneralException(ErrorStatus.ITEM_NOT_FOUND);
        }

        if (item.getStatus() != Status.ACTIVE) {
            throw new GeneralException(ErrorStatus.ITEM_NOT_AVAILABLE);
        }

        Assists assist = assistsRepository.findByNameAndDeletedFalse(request.assistName())
                .orElseThrow(() -> new GeneralException(ErrorStatus.ASSIST_NOT_FOUND));

        RentalHistory rentalHistory = request.toEntity(assist, user, item);

        rentalRepository.save(rentalHistory);
        item.updateStatus(Status.INACTIVE);

        return RentalItemResponse.from(rentalHistory);
    }
}