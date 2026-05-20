package ssurent.ssurentbe.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssurent.ssurentbe.domain.users.entity.UserPenaltyLog;
import ssurent.ssurentbe.domain.users.entity.Users;

import java.util.List;

public interface UserPenaltyLogRepository extends JpaRepository<UserPenaltyLog, Long> {

    List<UserPenaltyLog> findByUserIdOrderByCreatedAtDesc(Users user);

    long countByUserId(Users user);
}
