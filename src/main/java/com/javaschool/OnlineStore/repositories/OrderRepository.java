package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javaschool.OnlineStore.models.OrderEntity;
import com.javaschool.OnlineStore.models.UserEntity;

import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUser(UserEntity user);

    @Query("SELECT SUM(o.totalPrice) FROM OrderEntity o WHERE MONTH(o.orderDate) = :month AND YEAR(o.orderDate) = :year")
    Double getTotalRevenueByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT WEEK(o.orderDate) as week, SUM(o.totalPrice) as weeklyRevenue " +
            "FROM OrderEntity o " +
            "WHERE MONTH(o.orderDate) = :month AND YEAR(o.orderDate) = :year " +
            "GROUP BY week")
    List<Map<String, Object>> getWeeklyRevenueByMonth(@Param("month") int month, @Param("year") int year);
}
