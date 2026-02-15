package ssurent.ssurentbe.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCommandService {
    private final UserRepository userRepository;

    public Users getUserInfo(String username) {
        return userRepository.findByStudentNumAndDeletedFalse(username)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }
    @Transactional
    public void updatePhoneNumber(String username, String phoneNum) {
        Users user = getUserInfo(username);

        user.updatePhoneNumber(phoneNum);
    }
}
