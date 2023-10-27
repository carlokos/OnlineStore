package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.ProductDto;
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
        dto.setCategory_id(productEntity.getCategory().getId());
        return dto;
    }
}
