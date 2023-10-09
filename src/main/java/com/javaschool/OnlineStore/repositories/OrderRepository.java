package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
