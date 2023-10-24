package com.javaschool.OnlineStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.ClientAddressEntity;
import com.javaschool.OnlineStore.models.UserEntity;

public interface ClientAddressRepository extends JpaRepository<ClientAddressEntity, Long>{
    List<ClientAddressEntity> findAllByUser(UserEntity user);
}
