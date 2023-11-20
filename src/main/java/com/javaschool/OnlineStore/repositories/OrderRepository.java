package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javaschool.OnlineStore.models.OrderEntity;
import com.javaschool.OnlineStore.models.UserEntity;

import java.util.List;


public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
    List<OrderEntity> findByUser(UserEntity user);
    @Query("SELECT SUM(o.totalPrice) FROM OrderEntity o WHERE MONTH(o.orderDate) = :month")
    Double getTotalRevenueByMonth(@Param("month") int month);

    @Query("SELECT SUM(o.totalPrice) FROM OrderEntity o WHERE WEEK(o.orderDate) = :week")
    Double getTotalRevenueByWeek(@Param("week") int week);
}
