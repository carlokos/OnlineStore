package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.CartDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.CartMapper;
import com.javaschool.OnlineStore.models.CartEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.CartRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;
import com.javaschool.OnlineStore.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public List<CartDto> getUserCart(Long userId){
        return cartRepository.findByUser(loadUser(userId)).stream()
            .map(this::createCartDto)
            .toList();
    }

    public CartDto newCart(CartDto dto){
        CartEntity cart = mapDtoToEntity(dto, new CartEntity());

        cartRepository.save(cart);
        return createCartDto(cart);
    }

    public void updateCart(Long id, CartDto dto){
        CartEntity cart = loadCart(id);
        mapDtoToEntity(dto, cart);

        cartRepository.save(cart);
    }

    public void deleteCart(Long id){
        cartRepository.deleteById(id);
    }

    private CartEntity loadCart(Long id){
        return cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }
    private UserEntity loadUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ProductEntity loadProduct(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private CartDto createCartDto(CartEntity entity){
        return cartMapper.createCartDto(entity);
    }

    private CartEntity mapDtoToEntity(CartDto dto, CartEntity entity){
        return cartMapper.mapDtoToEntity(dto, entity, 
        loadProduct(dto.getProduct_id()), loadUser(dto.getUser_id()));
    }
}
