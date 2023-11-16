package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.OrderDetailEntity;
import com.javaschool.OnlineStore.models.OrderEntity;

import java.util.List;


public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long>{
    List<OrderDetailEntity> findByOrder(OrderEntity order);
}
