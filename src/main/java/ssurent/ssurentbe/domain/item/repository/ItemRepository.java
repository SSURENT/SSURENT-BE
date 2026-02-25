package ssurent.ssurentbe.domain.item.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssurent.ssurentbe.domain.item.entity.Items;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Items,Long> {
    @Query("SELECT i " +
            "FROM Items i " +
            "WHERE i.categoryId.id = :categoryId " +
            "AND i.status='ACTIVE'")
    List<Items> findActiveByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT i " +
            "FROM Items i " +
            "WHERE i.categoryId.id = :categoryId ")
    List<Items> findAllByCategoryId(@Param("categoryId") Long categoryId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Items i WHERE i.id = :id")
    Optional<Items> findByIdWithLock(@Param("id") Long id);

    // Repository
    boolean existsByItemNumAndName(String itemNum, String itemName);

    List<Items> findByNameStartingWith(String keyword);
}
