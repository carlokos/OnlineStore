package com.javaschool.OnlineStore.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javaschool.OnlineStore.dtos.ProductDto;
import com.javaschool.OnlineStore.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProducts(){
		List<ProductDto> result = productService.getAllProducts();
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
		ProductDto result = productService.getProductById(id);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@PathVariable Long id){
		List<ProductDto> result = productService.getAllProductsByCategory(id);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/top-selling")
	public ResponseEntity<List<Object[]>> getTopSellingProducts(){
		List<Object[]> result = productService.getTopSellingProducts();
		return ResponseEntity.ok(result);
	}

	@PostMapping
	public ResponseEntity<String> createNewProduct(@RequestBody ProductDto dto){
		productService.createNewProduct(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Product created succesfully");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDto dto){
		productService.updateProduct(id, dto);
		return ResponseEntity.ok("Product updated succesfully");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted succesfully");
	}
}
