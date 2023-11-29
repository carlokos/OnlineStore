package com.javaschool.OnlineStore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.RoleEntity;

public interface RolRespository extends JpaRepository<RoleEntity, Long>{
    Optional<RoleEntity> findByName(String name);
    
}
