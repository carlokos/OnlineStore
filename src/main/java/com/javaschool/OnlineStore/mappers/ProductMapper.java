package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.ProductDto;
import com.javaschool.OnlineStore.models.CategoryEntity;
import com.javaschool.OnlineStore.models.ProductEntity;

@Service
public class ProductMapper {
    public ProductDto createProductDto(ProductEntity productEntity){
        ProductDto dto = new ProductDto();
        dto.setId(productEntity.getId());
        dto.setTitle(productEntity.getTitle());
        dto.setPrice(productEntity.getPrice());
        dto.setBrand(productEntity.getBrand());
        dto.setColor(productEntity.getColor());
        dto.setStock(productEntity.getStock());
        dto.setVolume(productEntity.getVolume());
        dto.setWeight(productEntity.getWeight());
        dto.setCategoryId(productEntity.getCategory().getId());
        return dto;
    }

    public ProductEntity mapDtoToEntity(ProductDto dto, ProductEntity entity,
    CategoryEntity categoryEntity){
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        entity.setBrand(dto.getBrand());
        entity.setColor(dto.getColor());
        entity.setStock(dto.getStock());
        entity.setVolume(dto.getVolume());
        entity.setWeight(dto.getWeight());
        entity.setCategory(categoryEntity);
        return entity;
    }
}
