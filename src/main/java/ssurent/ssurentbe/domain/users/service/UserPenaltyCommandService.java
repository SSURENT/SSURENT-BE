package ssurent.ssurentbe.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.users.dto.request.AdminBulkUserUpdateRequest;
import ssurent.ssurentbe.domain.users.dto.request.AdminUserPenaltyCreateRequest;
import ssurent.ssurentbe.domain.users.entity.UserPenaltyLog;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.PenaltyTypes;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.repository.UserPenaltyLogRepository;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void bulkUpsertUsers(List<AdminBulkUserUpdateRequest> requests) {

        try{
            for (AdminBulkUserUpdateRequest req : requests) {
                Optional<Users> existing = userRepository.findByStudentNum(req.studentNum());

                if (existing.isPresent()) {
                    Users user = existing.get();
                    user.updateInfo(req.name(), req.phoneNum());
                } else {
                    Users user = Users.builder()
                            .studentNum(req.studentNum())
                            .name(req.name())
                            .phoneNum(req.phoneNum())
                            .password(
                                    req.phoneNum().isBlank()
                                            ? req.studentNum()
                                            : req.phoneNum()
                            )
                            .role(Role.NORMAL)
                            .deleted(false)
                            .build();
                    userRepository.save(user);
                }
            }
        }
        catch (Exception e){
            throw new GeneralException(ErrorStatus.USER_UPSERT_FAILED);
        }
    }
}
