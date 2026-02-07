package ssurent.ssurentbe.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssurent.ssurentbe.domain.users.entity.UserPanaltyLog;
import ssurent.ssurentbe.domain.users.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UserPanaltyLogRepository extends JpaRepository<UserPanaltyLog, Long> {

    List<UserPanaltyLog> findByUserIdOrderByCreatedAtDesc(Users user);
}
