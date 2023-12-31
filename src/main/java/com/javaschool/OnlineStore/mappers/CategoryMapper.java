package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.CategoryDto;
import com.javaschool.OnlineStore.models.CategoryEntity;

@Service
public class CategoryMapper {
    public CategoryDto createCategoryDto(CategoryEntity entity){
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public CategoryEntity mapDtoToEntity(CategoryDto dto, CategoryEntity entity){
        entity.setName(dto.getName());
        return entity;
    }
}
