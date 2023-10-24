package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.OrderDetailEntity;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long>{

}
