package com.javaschool.OnlineStore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.javaschool.OnlineStore.models.ImageEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.repositories.ImageRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    private final ImageRepository imageRepository = Mockito.mock(ImageRepository.class);

    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);

    private final ImageService imageService = new ImageService(imageRepository, productRepository);

    @Test
    public void ImageService_UploadImage_ImageUploadedSuccessfully() throws IOException {
        // Arrange
        Long productId = 1L;
        ProductEntity productEntity = new ProductEntity();
        MultipartFile file = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", "test image".getBytes());

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        // Act
        String result = imageService.uploadImage(file, productId);

        // Assert
        assertTrue(result.contains("Image uploaded successfully"));
        verify(imageRepository, times(1)).save(any(ImageEntity.class));
    }

    @Test
    public void ImageService_GetInfoByImageName_ReturnsImageInfo() {
        // Arrange
        String imageName = "test.jpg";
        ImageEntity dbImageEntity = new ImageEntity();
        dbImageEntity.setName(imageName);
        dbImageEntity.setType("image/jpeg");
        dbImageEntity.setImageData(new byte[]{1, 2, 3});

        when(imageRepository.findByName(imageName)).thenReturn(Optional.of(dbImageEntity));

        // Act
        ImageEntity result = imageService.getInfoByImageByName(imageName);

        // Assert
        assertNotNull(result);
        assertEquals(imageName, result.getName());
        assertEquals("image/jpeg", result.getType());
    }

    @Test
    public void ImageService_GetImage_ReturnsImageData() {
        // Arrange
        String imageName = "test.jpg";
        ImageEntity dbImageEntity = new ImageEntity();
        dbImageEntity.setImageData(new byte[]{1, 2, 3});

        when(imageRepository.findByName(imageName)).thenReturn(Optional.of(dbImageEntity));

        // Act
        byte[] result = imageService.getImage(imageName);

        // Assert
        assertNotNull(result);
    }
}

