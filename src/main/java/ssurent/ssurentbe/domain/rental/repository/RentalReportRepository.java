package ssurent.ssurentbe.domain.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssurent.ssurentbe.domain.rental.entity.RentalReport;

import java.util.List;

public interface RentalReportRepository extends JpaRepository<RentalReport, Long> {

    long countByResolvedFalse();

    @Query("SELECT r FROM RentalReport r JOIN FETCH r.rentalHistory rh JOIN FETCH rh.itemId WHERE r.resolved = false")
    List<RentalReport> findAllByResolvedFalse();
}