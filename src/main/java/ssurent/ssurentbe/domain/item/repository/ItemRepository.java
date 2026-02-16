package ssurent.ssurentbe.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssurent.ssurentbe.domain.item.entity.Items;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Items,Long> {
    @Query("SELECT i FROM Items i WHERE i.categoryId.id = :categoryId")
    List<Items> findByCategoryId(@Param("categoryId") Long categoryId);
}
