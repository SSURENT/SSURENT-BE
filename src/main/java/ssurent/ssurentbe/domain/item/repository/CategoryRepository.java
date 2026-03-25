package ssurent.ssurentbe.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssurent.ssurentbe.domain.item.entity.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();

    @Query(value = "SELECT * FROM categories", nativeQuery = true)
    List<Category> findAllIncludingDeleted();

    boolean existsByName(String name);

    Optional<Category> findByName(String s);

    @Modifying
    @Query(value = "DELETE FROM categories WHERE is_deleted = true AND deleted_at < :threshold", nativeQuery = true)
    int hardDeleteSoftDeletedBefore(@Param("threshold") LocalDateTime threshold);
}
