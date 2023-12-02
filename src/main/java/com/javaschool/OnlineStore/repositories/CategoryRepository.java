package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

}
