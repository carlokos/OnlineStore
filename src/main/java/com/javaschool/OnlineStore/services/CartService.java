package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaschool.OnlineStore.dtos.CartDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.CartMapper;
import com.javaschool.OnlineStore.models.CartEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.CartRepository;
import com.javaschool.OnlineStore.repositories.ProductRepository;
import com.javaschool.OnlineStore.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Transactional(readOnly = true)
    public List<CartDto> getUserCart(Long userId){
        return cartRepository.findByUser(loadUser(userId)).stream()
            .map(this::createCartDto)
            .toList();
    }

    @Transactional
    public void modifyProductCart(Long user_id, Long product_id){
        CartEntity cart = loadCartByUserAndProduct(user_id, product_id);

        if(cart != null){
            cart.setQuantity(cart.getQuantity() + 1);
            cartRepository.save(cart);
        }
    }

    @Transactional
    public CartDto newCart(CartDto dto){
        CartEntity cart = mapDtoToEntity(dto, new CartEntity());

        cartRepository.save(cart);
        return createCartDto(cart);
    }

    @Transactional
    public void updateCart(Long id, CartDto dto){
        CartEntity cart = loadCart(id);
        mapDtoToEntity(dto, cart);

        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Long id){
        cartRepository.deleteById(id);
    }

    @Transactional
    public void clearUserCart(Long id){
        List<CartEntity> userCart = cartRepository.findByUser(loadUser(id));
        cartRepository.deleteAll(userCart);
    }

    private CartEntity loadCart(Long id){
        return cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    private CartEntity loadCartByUserAndProduct(Long userId, Long productId){
        return cartRepository.findByUserIdAndProductId(userId, productId);
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
        loadProduct(dto.getProductId()), loadUser(dto.getUserId()));
    }
}
