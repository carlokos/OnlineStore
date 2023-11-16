package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.OrderDetailDto;
import com.javaschool.OnlineStore.models.OrderDetailEntity;
import com.javaschool.OnlineStore.models.OrderEntity;
import com.javaschool.OnlineStore.models.ProductEntity;

@Service
public class OrderDetailMapper {
    public OrderDetailDto createOrderDetailDto(OrderDetailEntity entity){
        OrderDetailDto dto = new OrderDetailDto();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setOrderId(entity.getOrder().getId());
        dto.setProductId(entity.getProduct().getId());
        return dto;
    }

    public OrderDetailEntity mapDtoToEntity(OrderDetailDto dto, OrderDetailEntity entity,
    OrderEntity order, ProductEntity product){
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());
        entity.setOrder(order);
        entity.setProduct(product);
        return entity;
    }
}
