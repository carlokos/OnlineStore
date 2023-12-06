package com.javaschool.OnlineStore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javaschool.OnlineStore.dtos.CartDto;
import com.javaschool.OnlineStore.mappers.CartMapper;
import com.javaschool.OnlineStore.models.CartEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.CartRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;
import com.javaschool.OnlineStore.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    private final CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private final CartMapper cartMapper = Mockito.mock(CartMapper.class);
    private final CartService cartService = new CartService(cartRepository, userRepository, productRepository, cartMapper);

    @Test
    public void CartService_GetUserCart_ReturnsUserCart() {
        // Arrange
        Long userId = 1L;
        List<CartEntity> userCartEntities = Collections.singletonList(new CartEntity());
        List<CartDto> expectedUserCartDtos = Collections.singletonList(new CartDto());

        //Mock
        when(cartRepository.findByUser(any())).thenReturn(userCartEntities);
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));
        when(cartMapper.createCartDto(any())).thenReturn(new CartDto());

        // Act
        List<CartDto> actualUserCartDtos = cartService.getUserCart(userId);

        // Assert
        assertNotNull(actualUserCartDtos);
        assertEquals(expectedUserCartDtos.size(), actualUserCartDtos.size());
    }

    @Test
    public void CartService_ModifyProductCart_IncreasesProductQuantity() {
        // Arrange
        Long userId = 1L;
        Long productId = 1L;
        
        CartEntity cartEntity = CartEntity.builder()
            .id(1L)
            .quantity(1)
            .build();    

        //Mock    
        when(cartRepository.findByUserIdAndProductId(userId, productId)).thenReturn(cartEntity);

        // Act
        cartService.modifyProductCart(userId, productId);

        // Assert
        assertEquals(2, cartEntity.getQuantity());
        verify(cartRepository, times(1)).save(cartEntity);
    }

    @Test
    public void CartService_NewCart_ReturnsCreatedCartDto() {
        // Arrange
        CartDto cartDto = CartDto.builder()
            .userId(1L)
            .productId(2L)
            .quantity(3)
            .build();

        CartEntity createdCartEntity = CartEntity.builder()
            .id(1L)
            .user(new UserEntity())
            .product(new ProductEntity())
            .quantity(3)
            .build();

        ProductEntity product = new ProductEntity();
        UserEntity user = new UserEntity();

        //Mock
        when(cartMapper.mapDtoToEntity(any(), any(), any(), any())).thenReturn(createdCartEntity);
        when(cartRepository.save(any(CartEntity.class))).thenReturn(createdCartEntity);
        when(cartMapper.createCartDto(createdCartEntity)).thenReturn(cartDto);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        // Act
        CartDto result = cartService.newCart(cartDto);

        // Assert
        assertNotNull(result);
        assertEquals(cartDto.getQuantity(), result.getQuantity());
    }

    @Test
    public void CartService_UpdateCart_CartIsUpdated() {
        // Arrange
        Long cartId = 1L;
        CartDto updatedCartDto = CartDto.builder()
            .id(cartId)
            .userId(2L)
            .productId(3L)
            .quantity(4)
            .build();

        CartEntity existingCartEntity = CartEntity.builder()
            .id(cartId)
            .user(new UserEntity())
            .product(new ProductEntity())
            .quantity(1)
            .build();

        CartEntity updatedCartEntity = existingCartEntity;

        updatedCartEntity.setQuantity(4);

        //Mock
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(existingCartEntity));
        when(cartMapper.mapDtoToEntity(any(), any(), any(), any())).thenReturn(updatedCartEntity);
        when(cartRepository.save(existingCartEntity)).thenReturn(existingCartEntity);
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));
        when(productRepository.findById(any())).thenReturn(Optional.of(new ProductEntity()));

        // Act
        cartService.updateCart(cartId, updatedCartDto);

        // Assert
        assertEquals(updatedCartDto.getQuantity(), existingCartEntity.getQuantity());
        verify(cartRepository, times(1)).save(existingCartEntity);
    }

    @Test
    public void CartService_DeleteCart_CartIsDeleted() {
        // Arrange
        Long cartId = 1L;

        // Act
        cartService.deleteCart(cartId);

        // Assert
        verify(cartRepository, times(1)).deleteById(cartId);
    }

    @Test
    public void CartService_ClearUserCart_UserCartIsCleared() {
        // Arrange
        Long userId = 1L;
        List<CartEntity> userCartEntities = Arrays.asList(
            CartEntity.builder().id(1L).user(new UserEntity()).product(new ProductEntity()).quantity(2).build(),
            CartEntity.builder().id(2L).user(new UserEntity()).product(new ProductEntity()).quantity(3).build()
        );

        //Mock
        when(cartRepository.findByUser(any())).thenReturn(userCartEntities);
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));

        // Act
        cartService.clearUserCart(userId);

        // Assert
        verify(cartRepository, times(1)).deleteAll(userCartEntities);
    }
}

