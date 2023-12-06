package com.javaschool.OnlineStore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javaschool.OnlineStore.dtos.ProductDto;
import com.javaschool.OnlineStore.mappers.ProductMapper;
import com.javaschool.OnlineStore.models.CategoryEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.repositories.CategoryRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private final ProductMapper productMapper = Mockito.mock(ProductMapper.class);
    private final CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);

    private final ProductService productService = new ProductService(productRepository, productMapper, categoryRepository);

    @Test
    public void ProductService_GetAllProducts_ReturnsAllProducts() {
        // Arrange
        List<ProductEntity> products = Collections.singletonList(ProductEntity.builder().build());
        List<ProductDto> expectedProductDtos = Collections.singletonList(ProductDto.builder().build());

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.createProductDto(any())).thenReturn(expectedProductDtos.get(0));

        // Act
        List<ProductDto> actualProductDtos = productService.getAllProducts();

        // Assert
        assertNotNull(actualProductDtos);
        assertEquals(expectedProductDtos.size(), actualProductDtos.size());
    }

    @Test
    public void ProductService_GetAllProductsByCategory_ReturnsProductsForCategory() {
        // Arrange
        Long categoryId = 1L;
        List<ProductEntity> products = Collections.singletonList(ProductEntity.builder().build());
        List<ProductDto> expectedProductDtos = Collections.singletonList(ProductDto.builder().build());

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(CategoryEntity.builder().build()));
        when(productRepository.findByCategory(any())).thenReturn(products);
        when(productMapper.createProductDto(any())).thenReturn(expectedProductDtos.get(0));

        // Act
        List<ProductDto> actualProductDtos = productService.getAllProductsByCategory(categoryId);

        // Assert
        assertNotNull(actualProductDtos);
        assertEquals(expectedProductDtos.size(), actualProductDtos.size());
    }

    @Test
    public void ProductService_CreateNewProduct_CreatesAndReturnsProductDto() {
        // Arrange
        ProductDto dto = ProductDto.builder().title("New Product").build();
        ProductEntity productEntity = ProductEntity.builder().title("New Product").build();

        //Mocks
        when(productMapper.mapDtoToEntity(any(), any(), any())).thenReturn(productEntity);
        when(productRepository.findByTitle(dto.getTitle())).thenReturn(Optional.empty());
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.createProductDto(productEntity)).thenReturn(dto);

        when(categoryRepository.findById(any())).thenReturn(Optional.of(CategoryEntity.builder().build()));

        // Act
        ProductDto result = productService.createNewProduct(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    public void ProductService_UpdateProduct_UpdatesProduct() {
        // Arrange
        Long productId = 1L;
        ProductDto dto = ProductDto.builder().build();
        ProductEntity productEntity = ProductEntity.builder().build();

        //Mocks
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productMapper.mapDtoToEntity(any(), any(), any())).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(categoryRepository.findById(any())).thenReturn(Optional.of(CategoryEntity.builder().build()));

        // Act
        productService.updateProduct(productId, dto);

        // Assert
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    public void ProductService_DeleteProduct_DeletesProduct() {
        // Arrange
        Long productId = 1L;
        ProductEntity productEntity = ProductEntity.builder().build();

        //Mocks
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }
}

