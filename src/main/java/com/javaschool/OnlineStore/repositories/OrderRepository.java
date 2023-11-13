package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.OrderEntity;
import com.javaschool.OnlineStore.models.UserEntity;

import java.util.List;


public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
    List<OrderEntity> findByUser(UserEntity user);
}
