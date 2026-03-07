package ssurent.ssurentbe.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.users.dto.request.AdminUserPenaltyCreateRequest;
import ssurent.ssurentbe.domain.users.entity.UserPenaltyLog;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.PenaltyTypes;
import ssurent.ssurentbe.domain.users.repository.UserPenaltyLogRepository;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserPenaltyCommandService {
    private final UserRepository userRepository;
    private final UserPenaltyLogRepository userPenaltyLogRepository;

    @Transactional
    public void createPenalty(AdminUserPenaltyCreateRequest request) {
        Users user = userRepository.findByIdAndDeletedFalse(request.userId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        PenaltyTypes penaltyType = PenaltyTypes.valueOf(request.penaltyType());

        UserPenaltyLog penaltyLog = UserPenaltyLog.builder()
                .userId(user)
                .itemName(request.itemName())
                .penaltyType(penaltyType)
                .build();

        userPenaltyLogRepository.save(penaltyLog);
    }

    @Transactional
    public void deletePenalty(Long penaltyId) {
        UserPenaltyLog userPenaltyLog = userPenaltyLogRepository.findById(penaltyId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PENALTY_NOT_FOUND));
        userPenaltyLogRepository.delete(userPenaltyLog);
    }
}
