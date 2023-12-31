package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaschool.OnlineStore.dtos.CategoryDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.CategoryMapper;
import com.javaschool.OnlineStore.models.CategoryEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.repositories.CategoryRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::createCategoryDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        CategoryEntity categoryEntity = loadCategory(id);
        return createCategoryDto(categoryEntity);
    }

    @Transactional
    public void updateCategory(Long id, CategoryDto dto) {
        CategoryEntity category = loadCategory(id);
        categoryMapper.mapDtoToEntity(dto, category);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        CategoryEntity defaultCategory = loadCategory(Long.valueOf(1L));
        List<ProductEntity> products = productRepository.findByCategory(loadCategory(id));
        if (!products.isEmpty()) {
            for (ProductEntity product : products) {
                product.setCategory(defaultCategory);
                productRepository.save(product);
            }
        }
        CategoryEntity toDelete = loadCategory(id);
        toDelete.getProducts().clear();
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryDto createNewCategory(CategoryDto dto) {
        CategoryEntity entity = categoryMapper.mapDtoToEntity(dto, new CategoryEntity());
        categoryRepository.save(entity); 
        return createCategoryDto(entity);
    }

    private CategoryEntity loadCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private CategoryDto createCategoryDto(CategoryEntity entity) {
        return categoryMapper.createCategoryDto(entity);
    }
}
