package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.CartEntity;
import com.javaschool.OnlineStore.models.UserEntity;

import java.util.List;


public interface CartRepository extends JpaRepository<CartEntity, Long>{
    List<CartEntity> findByUser(UserEntity user);
    CartEntity findByUser_IdAndProduct_Id(Long userId, Long productId);
}