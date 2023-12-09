package com.javaschool.OnlineStore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javaschool.OnlineStore.dtos.CategoryDto;
import com.javaschool.OnlineStore.mappers.CategoryMapper;
import com.javaschool.OnlineStore.models.CategoryEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.repositories.CategoryRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    private final CategoryRepository categoryRepositoryMock = Mockito.mock(CategoryRepository.class);
    private final ProductRepository productRepositoryMock = Mockito.mock(ProductRepository.class);
    private final CategoryMapper categoryMapperMock = Mockito.mock(CategoryMapper.class);
    private final CategoryService categoryService = new CategoryService(categoryRepositoryMock, productRepositoryMock, categoryMapperMock);
    
    @Test
    public void CategoryService_CreateCategory_ReturnsCategoryDto() {
        // Arrange
        CategoryEntity category = CategoryEntity.builder()
                .id(5L)
                .name("Category")
                .build();

        CategoryDto dto = CategoryDto.builder()
                .name("Category")
                .build();

        // Mocking behavior of CategoryRepository
        when(categoryRepositoryMock.save(any(CategoryEntity.class))).thenReturn(category);
        when(categoryMapperMock.createCategoryDto(any())).thenReturn(dto);
        when(categoryMapperMock.mapDtoToEntity(any(), any())).thenReturn(category);

        // Act
        CategoryDto savedCategory = categoryService.createNewCategory(dto);

        // Assert
        assertNotNull(savedCategory);
        assertEquals(dto.getName(), savedCategory.getName(), "Name should match");

        // Verify that the save method was called on the repository
        verify(categoryRepositoryMock, times(1)).save(any(CategoryEntity.class));
    }

    @Test
    public void CategoryService_GetAllCategories_ReturnsListOfCategoryDto() {
        // Arrange
        CategoryEntity category1 = CategoryEntity.builder()
                .id(1L)
                .name("Category1")
                .build();

        CategoryEntity category2 = CategoryEntity.builder()
                .id(2L)
                .name("Category2")
                .build();

        List<CategoryEntity> categoryEntities = Arrays.asList(category1, category2);

        when(categoryRepositoryMock.findAll()).thenReturn(categoryEntities);

        CategoryDto categoryDto1 = CategoryDto.builder()
                .name("Category1")
                .build();

        CategoryDto categoryDto2 = CategoryDto.builder()
                .name("Category2")
                .build();

        when(categoryMapperMock.createCategoryDto(category1)).thenReturn(categoryDto1);
        when(categoryMapperMock.createCategoryDto(category2)).thenReturn(categoryDto2);

        // Act
        List<CategoryDto> allCategories = categoryService.getAllCategories();

        // Assert
        assertNotNull(allCategories);
        assertEquals(2, allCategories.size(), "Expected size of the category list is 2");
        assertEquals("Category1", allCategories.get(0).getName(), "Name of the first category should match");
        assertEquals("Category2", allCategories.get(1).getName(), "Name of the second category should match");

        // Verify that the findAll method was called on the repository
        verify(categoryRepositoryMock, times(1)).findAll();
    }

    @Test
    public void CategoryService_GetCategoryById_ReturnsCategoryDto() {
        // Arrange
        Long categoryId = 1L;

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(categoryId)
                .name("TestCategory")
                .build();

        when(categoryRepositoryMock.findById(categoryId)).thenReturn(java.util.Optional.of(categoryEntity));

        CategoryDto categoryDto = CategoryDto.builder()
                .id(categoryId)
                .name("TestCategory")
                .build();

        when(categoryMapperMock.createCategoryDto(categoryEntity)).thenReturn(categoryDto);

        // Act
        CategoryDto retrievedCategory = categoryService.getCategoryById(categoryId);

        // Assert
        assertNotNull(retrievedCategory);
        assertEquals(categoryId, retrievedCategory.getId(), "ID should match");
        assertEquals("TestCategory", retrievedCategory.getName(), "Name should match");

        // Verify that the findById method was called on the repository
        verify(categoryRepositoryMock, times(1)).findById(categoryId);
    }

    @Test
    public void CategoryService_UpdateCategory_SuccessfullyUpdatesCategory() {
        // Arrange
        Long categoryId = 1L;
        CategoryDto updatedDto = CategoryDto.builder()
                .name("UpdatedCategoryName")
                .build();

        CategoryEntity existingCategory = CategoryEntity.builder()
                .id(categoryId)
                .name("OriginalCategoryName")
                .build();

        when(categoryRepositoryMock.findById(categoryId)).thenReturn(java.util.Optional.of(existingCategory));

        // Act
        categoryService.updateCategory(categoryId, updatedDto);

        // Assert
        verify(categoryMapperMock, times(1)).mapDtoToEntity(updatedDto, existingCategory);
        verify(categoryRepositoryMock, times(1)).save(existingCategory);

        // Verify that the findById method was called on the repository
        verify(categoryRepositoryMock, times(1)).findById(categoryId);
    }

    @Test
    public void CategoryService_DeleteCategory_SuccessfullyDeletesCategory() {
        // Arrange
        Long categoryId = 1L;
        Long defaultCategoryId = 0L;
        CategoryEntity defaultCategory = CategoryEntity.builder()
            .id(defaultCategoryId)
            .name("Default category")
            .build();

        CategoryEntity categoryToDelete = CategoryEntity.builder()
                .id(categoryId)
                .name("CategoryToDelete")
                .products(new ArrayList<>())
                .build();

        ProductEntity productInCategoryToDelete = ProductEntity.builder()
                .id(1L)
                .title("some product")
                .category(categoryToDelete)
                .build();

        //Mocks
        when(categoryRepositoryMock.findById(defaultCategoryId)).thenReturn(java.util.Optional.of(defaultCategory));
        when(categoryRepositoryMock.findById(categoryId)).thenReturn(java.util.Optional.of(categoryToDelete));
        when(productRepositoryMock.findByCategory(categoryToDelete)).thenReturn(Collections.singletonList(productInCategoryToDelete));

        // Act
        categoryService.deleteCategory(categoryId);

        // Assert
        // Verify that the findById method was called on the repository
        verify(categoryRepositoryMock, times(2)).findById(categoryId);
        
        // Verify that the findByCategory method was called on the productRepository
        verify(productRepositoryMock, times(1)).findByCategory(categoryToDelete);

        // Verify that setCategory and save were called on each product
        verify(productRepositoryMock, times(1)).save(any(ProductEntity.class));

        // Verify that deleteById was called on the categoryRepository
        verify(categoryRepositoryMock, times(1)).deleteById(categoryId);
    }
}
