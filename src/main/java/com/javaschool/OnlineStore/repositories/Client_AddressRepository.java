package com.javaschool.OnlineStore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.Client_addressEntity;
import com.javaschool.OnlineStore.models.UserEntity;

public interface Client_AddressRepository extends JpaRepository<Client_addressEntity, Long>{
    List<Client_addressEntity> findAllByUser(UserEntity user);
}
