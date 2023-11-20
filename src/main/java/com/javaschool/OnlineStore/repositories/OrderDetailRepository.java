package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javaschool.OnlineStore.models.OrderDetailEntity;
import com.javaschool.OnlineStore.models.OrderEntity;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long>{
    List<OrderDetailEntity> findByOrder(OrderEntity order);
    Long countByOrder(OrderEntity order);
    @Query("SELECT p.category.name, SUM(od.quantity) as totalSold "
            + "FROM OrderDetailEntity od "
            + "JOIN od.product p "
            + "GROUP BY p.category.name "
            + "ORDER BY totalSold DESC")
    List<Object[]> getTopSellingCategories();
}
