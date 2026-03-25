package ssurent.ssurentbe.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.Role;
import ssurent.ssurentbe.domain.users.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByStudentNum(String studentNum);
    boolean existsByStudentNum(String studentNum);
    Optional<Users> findByStudentNumAndDeletedFalse(String studentNum);
    List<Users> findByDeletedFalse();
    List<Users> findByStatusAndDeletedFalse(Status status);
    List<Users> findByRoleInAndDeletedFalse(List<Role> roles);
    Optional<Users> findByIdAndDeletedFalse(Long userId);
    Optional<Users> findByPhoneNumAndDeletedFalse(String phoneNum);
    @Query("SELECT u FROM Users u WHERE REPLACE(u.phoneNum, '-', '') = :phoneNum AND u.deleted = false")
    Optional<Users> findByNormalizedPhoneNumAndDeletedFalse(@Param("phoneNum") String phoneNum);

    @Modifying
    @Query(value = "DELETE FROM users WHERE is_deleted = true AND deleted_at < :threshold", nativeQuery = true)
    int hardDeleteSoftDeletedBefore(@Param("threshold") LocalDateTime threshold);
}
