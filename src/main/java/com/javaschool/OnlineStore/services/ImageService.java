package com.javaschool.OnlineStore.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.ImageUtil;
import com.javaschool.OnlineStore.models.ImageEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.repositories.ImageRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    
    public String uploadImage(MultipartFile file, Long productId) throws IOException {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        ImageEntity image = ImageEntity.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes()))
                .product(product)
                .build();

        imageRepository.save(image);
        return "Image uploaded successfully: " + file.getOriginalFilename();
    }

    @Transactional
    public ImageEntity getInfoByImageByName(String name) {
        Optional<ImageEntity> dbImage = imageRepository.findByName(name);

        return ImageEntity.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .imageData(ImageUtil.decompressImage(dbImage.get().getImageData())).build();

    }

    @Transactional
    public byte[] getImage(String name) {
        Optional<ImageEntity> dbImage = imageRepository.findByName(name);
        byte[] image = ImageUtil.decompressImage(dbImage.get().getImageData());
        return image;
    }
}
