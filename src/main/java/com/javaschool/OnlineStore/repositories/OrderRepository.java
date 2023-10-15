package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

}
