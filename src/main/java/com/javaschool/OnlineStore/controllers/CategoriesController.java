package com.javaschool.OnlineStore.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaschool.OnlineStore.dtos.CategoryDto;
import com.javaschool.OnlineStore.services.CategoryService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoriesController {
		private final CategoryService categoryService;
		
		@GetMapping
		public ResponseEntity<List<CategoryDto>> getAllProducts(){
			List<CategoryDto> result = categoryService.getAllCategories();
			return ResponseEntity.ok(result);
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<CategoryDto> getProductById(@PathVariable Long id) {
			CategoryDto result = categoryService.getCategoryById(id);
			return ResponseEntity.ok(result);
		}
	
}
