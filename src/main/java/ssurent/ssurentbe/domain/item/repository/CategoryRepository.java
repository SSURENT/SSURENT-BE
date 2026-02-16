package ssurent.ssurentbe.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssurent.ssurentbe.domain.item.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
}
