package com.javaschool.OnlineStore.services;

import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaschool.OnlineStore.dtos.ProductDto;
import com.javaschool.OnlineStore.exceptions.ResourceConflictException;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.ImageUtil;
import com.javaschool.OnlineStore.mappers.ProductMapper;
import com.javaschool.OnlineStore.models.CategoryEntity;
import com.javaschool.OnlineStore.models.ImageEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.repositories.CategoryRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts(){
        return productRepository.findAll().stream()
            .map(this::createProductDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getTop10Products(){
        return productRepository.findTop10BestSoldProducts().stream()
            .map(this::createProductDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id){
        ProductEntity product = loadProduct(id);
        return createProductDto(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProductsByCategory(Long id){
        return productRepository.findByCategory(loadCategoryById(id)).stream()
            .map(this::createProductDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getTopSellingProducts(){
        return productRepository.findBestSoldProducts().stream()
            .toList();
    }

    @Transactional
    public ProductDto createNewProduct(ProductDto dto){
        ProductEntity product = mapDtoToEntity(dto, new ProductEntity());
        if(productRepository.findByTitle(dto.getTitle()).isPresent()){
            throw new ResourceConflictException("Product title already exist");
        }
        productRepository.save(product);
        return createProductDto(product);
    }

    @Transactional
    public void updateProduct(Long id, ProductDto dto){
        ProductEntity product = loadProduct(id);
        mapDtoToEntity(dto, product);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public String getFirstImageForProduct(Long productId) {
        ImageEntity dbImage = productRepository.findFirstImageByProductId(productId)
                .orElseThrow(() -> new RuntimeException("No images found for product with id: " + productId));
        byte[] image = ImageUtil.decompressImage(dbImage.getImageData());
        String base64Image = Base64.getEncoder().encodeToString(image);
        
        return base64Image;
    }

    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    private ProductEntity loadProduct(Long id){
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private CategoryEntity loadCategoryById(Long id){
        return categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private ProductDto createProductDto(ProductEntity productEntity){
        return productMapper.createProductDto(productEntity);
    }

    private ProductEntity mapDtoToEntity(ProductDto dto, ProductEntity entity){
        return productMapper.mapDtoToEntity(dto, entity, loadCategoryById(dto.getCategoryId()));
    }
}
