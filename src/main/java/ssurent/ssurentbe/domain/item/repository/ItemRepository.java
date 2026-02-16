package ssurent.ssurentbe.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssurent.ssurentbe.domain.item.entity.Items;

import java.util.List;

public interface ItemRepository extends JpaRepository<Items,Long> {
    @Query("SELECT i " +
            "FROM Items i " +
            "WHERE i.categoryId.id = :categoryId " +
            "AND i.status='ACTIVE'")
    List<Items> findActiveByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT i " +
            "FROM Items i " +
            "WHERE i.categoryId.id = :categoryId ")
    List<Items> findAllByCategoryId(@Param("categoryId") Long categoryId);


    // Repository
    boolean existsByItemNumAndName(String itemNum, String itemName);

    List<Items> findByNameStartingWith(String keyword);
}
