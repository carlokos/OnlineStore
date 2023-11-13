package com.javaschool.OnlineStore.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping
	public ResponseEntity<String> createNewCategory(@RequestBody CategoryDto dto){
		categoryService.createNewCategory(dto);
		return ResponseEntity.status(201).body("new category created succesfully");
	}
		
	@PutMapping("/{id}")
	public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody CategoryDto dto){
		categoryService.updateCategory(id, dto);
		return ResponseEntity.ok("Category updated succesfully");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id){
		categoryService.deleteCategory(id);
		return ResponseEntity.status(204).body("Category deleted succesfully");
	}
}
