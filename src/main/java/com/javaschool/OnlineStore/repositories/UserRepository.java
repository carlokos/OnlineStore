package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
