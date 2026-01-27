package ssurent.ssurentbe.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssurent.ssurentbe.domain.users.entity.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByStudentNum(String studentNum);
    boolean existsByStudentNum(String studentNum);
}
