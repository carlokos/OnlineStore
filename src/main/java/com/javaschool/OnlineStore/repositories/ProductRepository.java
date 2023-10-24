package com.javaschool.OnlineStore.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.CategoryEntity;
import com.javaschool.OnlineStore.models.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    Optional<ProductEntity> findByTitle(String title);
    List<ProductEntity> findByCategory(CategoryEntity category);
}