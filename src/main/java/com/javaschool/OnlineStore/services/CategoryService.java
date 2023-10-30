package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.CategoryDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.CategoryMapper;
import com.javaschool.OnlineStore.models.CategoryEntity;
import com.javaschool.OnlineStore.repositories.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories(){
        return categoryRepository.findAll().stream()
            .map(this::createCategoryDto)
            .toList();
    }

    public CategoryDto getCategoryById(Long id){
        CategoryEntity categoryEntity = loadCategory(id);
        return createCategoryDto(categoryEntity);
    }

    public void updateCategory(Long id, CategoryDto dto){
        CategoryEntity category = loadCategory(id);
        categoryMapper.mapDtoToEntity(dto, category);
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }

    public CategoryDto createNewCategory(CategoryDto dto){
        CategoryEntity entity = categoryMapper.mapDtoToEntity(dto, new CategoryEntity());
        categoryRepository.save(entity);
        return createCategoryDto(entity);
    }
    
    private CategoryEntity loadCategory(Long id){
        return categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
    private CategoryDto createCategoryDto(CategoryEntity entity){
        return categoryMapper.createCategoryDto(entity);
    }
}
