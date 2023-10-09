package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.Order_detail;

public interface OrderDetailRepository extends JpaRepository<Order_detail, Long>{

}
