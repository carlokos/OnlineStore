package com.javaschool.OnlineStore.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaschool.OnlineStore.models.CategoryEntity;
import com.javaschool.OnlineStore.repositories.CategoryRepository;


@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
		
		@Autowired
		private CategoryRepository categoryRepository;
		
		@GetMapping
		public List<CategoryEntity> getAllProducts(){
			return categoryRepository.findAll();
		}
		
		@GetMapping("/{id}")
		public Optional<CategoryEntity> getProductById(@PathVariable Long id) {
			return categoryRepository.findById(id);
		}
	
}
