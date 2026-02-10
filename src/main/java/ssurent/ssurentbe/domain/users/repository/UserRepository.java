package ssurent.ssurentbe.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssurent.ssurentbe.domain.users.entity.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByStudentNum(String studentNum);
    boolean existsByStudentNum(String studentNum);
    Optional<Users> findByStudentNumAndDeletedFalse(String studentNum);
}
