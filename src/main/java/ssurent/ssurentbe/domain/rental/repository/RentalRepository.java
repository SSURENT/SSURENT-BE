package ssurent.ssurentbe.domain.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalRepository extends JpaRepository<RentalHistory, Long> {

    @Query("SELECT rh FROM RentalHistory rh " +
            "JOIN FETCH rh.itemId i " +
            "WHERE rh.userId.id = :userId " +
            "AND (:startDate IS NULL OR rh.rentalDate >= :startDate) " +
            "AND (:endDate IS NULL OR rh.rentalDate <= :endDate) " +
            "AND (:itemName IS NULL OR i.name LIKE %:itemName%) " +
            "ORDER BY rh.rentalDate DESC")
    List<RentalHistory> findByUserIdAndFilters(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("itemName") String itemName
    );
}
