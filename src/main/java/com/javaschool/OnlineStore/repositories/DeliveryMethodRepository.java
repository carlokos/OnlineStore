package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.DeliveryMethodEntity;

public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethodEntity, Long>{
    
}
