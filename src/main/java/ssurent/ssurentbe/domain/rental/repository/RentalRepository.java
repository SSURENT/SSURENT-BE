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

    @Query("SELECT rh FROM RentalHistory rh " +
            "JOIN FETCH rh.userId u " +
            "JOIN FETCH rh.itemId i " +
            "JOIN FETCH i.categoryId " +
            "JOIN FETCH rh.assistId " +
            "LEFT JOIN FETCH rh.returnAssistId " +
            "WHERE (rh.rentalDate >= :startDate AND rh.rentalDate <= :endDate) " +
            "OR (rh.returnDate >= :startDate AND rh.returnDate <= :endDate) " +
            "ORDER BY rh.rentalDate ASC")
    List<RentalHistory> findAllByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // 카테고리별 집계 (대여 이력 없는 카테고리도 포함)
    @Query("SELECT c.id, c.name, COUNT(rh.id) " +
            "FROM Category c " +
            "LEFT JOIN Items i ON i.categoryId = c " +
            "LEFT JOIN RentalHistory rh ON rh.itemId = i " +
            "AND rh.rentalDate >= :startDate " +
            "AND rh.rentalDate <= :endDate " +
            "GROUP BY c.id, c.name " +
            "ORDER BY COUNT(rh.id) DESC")
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

    // 전체 물품 월별 집계
    @Query("SELECT YEAR(rh.rentalDate), MONTH(rh.rentalDate), COUNT(rh.id) " +
            "FROM RentalHistory rh " +
            "WHERE rh.rentalDate >= :startDate " +
            "AND rh.rentalDate <= :endDate " +
            "GROUP BY YEAR(rh.rentalDate), MONTH(rh.rentalDate) " +
            "ORDER BY YEAR(rh.rentalDate), MONTH(rh.rentalDate)")
    List<Object[]> countByMonthAndDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // 카테고리별 월별 집계
    @Query("SELECT YEAR(rh.rentalDate), MONTH(rh.rentalDate), COUNT(rh.id) " +
            "FROM RentalHistory rh " +
            "JOIN rh.itemId i " +
            "WHERE i.categoryId.id = :categoryId " +
            "AND rh.rentalDate >= :startDate " +
            "AND rh.rentalDate <= :endDate " +
            "GROUP BY YEAR(rh.rentalDate), MONTH(rh.rentalDate) " +
            "ORDER BY YEAR(rh.rentalDate), MONTH(rh.rentalDate)")
    List<Object[]> countByCategoryAndMonthAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
