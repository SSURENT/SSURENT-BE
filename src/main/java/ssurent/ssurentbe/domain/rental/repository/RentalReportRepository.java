package ssurent.ssurentbe.domain.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssurent.ssurentbe.domain.rental.entity.RentalReport;

public interface RentalReportRepository extends JpaRepository<RentalReport, Long> {
}