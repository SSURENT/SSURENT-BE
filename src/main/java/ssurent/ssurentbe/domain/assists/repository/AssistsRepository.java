package ssurent.ssurentbe.domain.assists.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssurent.ssurentbe.domain.assists.entity.Assists;

import java.util.Optional;

public interface AssistsRepository extends JpaRepository<Assists, Long> {
    Optional<Assists> findByNameAndDeletedFalse(String name);
}