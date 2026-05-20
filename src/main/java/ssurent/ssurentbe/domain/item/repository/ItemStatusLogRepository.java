package ssurent.ssurentbe.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssurent.ssurentbe.domain.item.entity.ItemStatusLog;

public interface ItemStatusLogRepository extends JpaRepository<ItemStatusLog, Long> {
}
