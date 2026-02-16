package ssurent.ssurentbe.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssurent.ssurentbe.domain.item.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    @Query(value = "SELECT * FROM categories", nativeQuery = true)
    List<Category> findAllIncludingDeleted();
    Boolean existsByName(String name);

    Optional<Category> findByName(String s);
}
