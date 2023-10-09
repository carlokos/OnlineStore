package com.javaschool.OnlineStore.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.javaschool.OnlineStore.models.Product;
import com.javaschool.OnlineStore.repositories.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Product> getProductById(@PathVariable Long id) {
		return productRepository.findById(id);
	}
}
