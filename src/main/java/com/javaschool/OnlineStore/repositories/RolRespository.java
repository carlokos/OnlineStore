package com.javaschool.OnlineStore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.RolEntity;

public interface RolRespository extends JpaRepository<RolEntity, Long>{
    Optional<RolEntity> findByName(String name);
    
}
