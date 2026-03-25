package ssurent.ssurentbe.domain.assists.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssurent.ssurentbe.domain.assists.entity.Assists;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AssistsRepository extends JpaRepository<Assists, Long> {
    Optional<Assists> findByNameAndDeletedFalse(String name);

    @Modifying
    @Query(value = "DELETE FROM assists WHERE is_deleted = true AND deleted_at < :threshold", nativeQuery = true)
    int hardDeleteSoftDeletedBefore(@Param("threshold") LocalDateTime threshold);
}