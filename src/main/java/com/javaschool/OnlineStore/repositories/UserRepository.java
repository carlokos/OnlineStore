package com.javaschool.OnlineStore.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<UserEntity> findAllByOrderByLoginCountDesc();
}
