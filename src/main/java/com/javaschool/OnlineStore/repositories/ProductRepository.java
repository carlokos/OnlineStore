package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
