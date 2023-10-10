package com.javaschool.OnlineStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaschool.OnlineStore.models.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{

}
