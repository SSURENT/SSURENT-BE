package ssurent.ssurentbe.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.users.dto.request.AdminBulkUserUpdateRequest;
import ssurent.ssurentbe.domain.users.dto.request.AdminUserStatusUpdateRequest;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCommandService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Users getUserInfo(String username) {
        return userRepository.findByStudentNumAndDeletedFalse(username)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }
    @Transactional
    public void updatePhoneNumber(String username, String phoneNum) {
        Users user = getUserInfo(username);

        user.updatePhoneNumber(phoneNum);
    }

    @Transactional
    public void changeStatus(AdminUserStatusUpdateRequest request) {
        Users user = userRepository.findByIdAndDeletedFalse(request.userId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        user.updateStatus(request.status());
    }

    @Transactional
    public void bulkUpsertUsers(List<AdminBulkUserUpdateRequest> requests) {

        try{
            for (AdminBulkUserUpdateRequest req : requests) {
                Optional<Users> existing = userRepository.findByStudentNumAndDeletedFalse(req.studentNum());

                if (existing.isPresent()) {
                    Users user = existing.get();
                    user.updateInfo(req.name(), req.phoneNum());
                    user.restore();
                } else {
                    Users user = Users.builder()
                            .studentNum(req.studentNum())
                            .name(req.name())
                            .phoneNum(req.phoneNum())
                            .password(passwordEncoder.encode(
                                    req.phoneNum().isBlank()
                                            ? req.studentNum()
                                            : req.phoneNum())
                            )
                            .role(Role.NORMAL)
                            .status(Status.ACTIVE)
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
