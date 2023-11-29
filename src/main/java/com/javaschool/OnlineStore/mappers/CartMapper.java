package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.CartDto;
import com.javaschool.OnlineStore.models.CartEntity;
import com.javaschool.OnlineStore.models.ProductEntity;
import com.javaschool.OnlineStore.models.UserEntity;

@Service
public class CartMapper {
    public CartDto createCartDto(CartEntity cartEntity){
        CartDto dto = new CartDto();
        dto.setId(cartEntity.getId());
        dto.setQuantity(cartEntity.getQuantity());
        dto.setUserId(cartEntity.getUser().getId());
        dto.setProductId(cartEntity.getProduct().getId());
        return dto;
    }

    public CartEntity mapDtoToEntity(CartDto dto, CartEntity cartEntity,
        ProductEntity productEntity, UserEntity userEntity){
           cartEntity.setId(dto.getId());
           cartEntity.setQuantity(dto.getQuantity());
           cartEntity.setProduct(productEntity);
           cartEntity.setUser(userEntity);
           return cartEntity;
        }
}
