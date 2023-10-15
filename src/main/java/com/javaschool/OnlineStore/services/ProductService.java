package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.ProductDto;
import com.javaschool.OnlineStore.exceptions.ResourceConflictException;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.ProductMapper;
import com.javaschool.OnlineStore.models.CategoryEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.repositories.CategoryRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public List<ProductDto> getAllProducts(){
        return productRepository.findAll().stream()
            .map(this::createProductDto)
            .toList();
    }

    public ProductDto getProductById(Long id){
        ProductEntity product = loadProduct(id);
        return createProductDto(product);
    }

    public ProductDto createNewProduct(ProductDto dto){
        ProductEntity product = mapDtoToEntity(dto, new ProductEntity());
        if(productRepository.findByTitle(dto.getTitle()).isPresent()){
            throw new ResourceConflictException("Product title already exist");
        }
        productRepository.save(product);
        return createProductDto(product);
    }

    public void updateProduct(Long id, ProductDto dto){
        ProductEntity product = loadProduct(id);
        mapDtoToEntity(dto, product);
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    private ProductEntity loadProduct(Long id){
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private CategoryEntity loadCategory(String name){
        return categoryRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private ProductDto createProductDto(ProductEntity productEntity){
        return productMapper.createProductDto(productEntity);
    }

    private ProductEntity mapDtoToEntity(ProductDto dto, ProductEntity entity){
        entity.setTitle(dto.getTitle());
        entity.setBrand(dto.getBrand());
        entity.setVolume(dto.getVolume());
        entity.setColor(dto.getColor());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setWeight(dto.getWeight());
        entity.setCategory(loadCategory(dto.getCategory()));
        return entity;
    }
}
