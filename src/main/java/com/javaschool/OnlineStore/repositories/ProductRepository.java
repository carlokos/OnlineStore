package com.javaschool.OnlineStore.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javaschool.OnlineStore.models.CategoryEntity;
import com.javaschool.OnlineStore.models.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByTitle(String title);

    List<ProductEntity> findByCategory(CategoryEntity category);

    @Query("SELECT p.id, p.title, p.brand, c.name AS categoryName, SUM(od.quantity) AS totalSold " +
            "FROM ProductEntity p " +
            "JOIN p.category c " +
            "JOIN p.Order_detail od " +
            "GROUP BY p.id, p.title, p.brand, c.name " +
            "ORDER BY totalSold DESC")
    List<Object[]> findBestSoldProducts();
}
