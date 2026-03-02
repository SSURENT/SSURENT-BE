package ssurent.ssurentbe.domain.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;

import ssurent.ssurentbe.domain.rental.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalRepository extends JpaRepository<RentalHistory, Long> {

    boolean existsByItemId_IdAndStatus(Long itemId, Status status);

    @Query("SELECT rh FROM RentalHistory rh " +
            "JOIN FETCH rh.itemId i " +
            "WHERE rh.userId.id = :userId " +
            "AND rh.status = :status " +
            "ORDER BY rh.rentalDate DESC")
    List<RentalHistory> findActiveRentalsByUserId(
            @Param("userId") Long userId,
            @Param("status") Status status
    );

    @Query("SELECT rh FROM RentalHistory rh " +
            "JOIN FETCH rh.itemId i " +
            "WHERE rh.userId.id = :userId " +
            "AND (:startDate IS NULL OR rh.rentalDate >= :startDate) " +
            "AND (:endDate IS NULL OR rh.rentalDate <= :endDate) " +
            "AND (:itemName IS NULL OR i.itemName LIKE %:itemName%) " +
            "ORDER BY rh.rentalDate DESC")
    List<RentalHistory> findByUserIdAndFilters(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("itemName") String itemName
    );

    // 카테고리별 집계
    @Query("SELECT i.categoryId.id, i.categoryId.name, COUNT(rh) " +
            "FROM RentalHistory rh " +
            "JOIN rh.itemId i " +
            "WHERE rh.rentalDate >= :startDate " +
            "AND rh.rentalDate <= :endDate " +
            "GROUP BY i.categoryId.id, i.categoryId.name " +
            "ORDER BY COUNT(rh) DESC")
    List<Object[]> countByCategoryAndDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    //항목별 집계
    @Query("SELECT i.id, i.itemName, i.itemNum, COUNT(rh) " +
            "FROM RentalHistory rh " +
            "JOIN rh.itemId i " +
            "WHERE i.categoryId.id = :categoryId " +
            "AND rh.rentalDate >= :startDate " +
            "AND rh.rentalDate <= :endDate " +
            "GROUP BY i.id, i.itemName, i.itemNum " +
            "ORDER BY COUNT(rh) DESC")
    List<Object[]> countByItemAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
