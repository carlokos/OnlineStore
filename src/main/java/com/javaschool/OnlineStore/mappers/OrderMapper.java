package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.OrderDto;
import com.javaschool.OnlineStore.models.ClientAddressEntity;
import com.javaschool.OnlineStore.models.DeliveryMethodEntity;
import com.javaschool.OnlineStore.models.OrderEntity;
import com.javaschool.OnlineStore.models.PaymentMethodEntity;
import com.javaschool.OnlineStore.models.UserEntity;

@Service
public class OrderMapper {
    public OrderDto createOrderDto(OrderEntity orderEntity){
        OrderDto dto = new OrderDto();
        dto.setId(orderEntity.getId());
        dto.setPaymentStatus(orderEntity.getPaymentStatus());
        dto.setOrderStatus(orderEntity.getOrderStatus());
        dto.setUserId(orderEntity.getUser().getId());
        dto.setAddressId(orderEntity.getClient_address().getId());
        dto.setPaymentId(orderEntity.getPayment().getId());
        dto.setDeliveryMethodId(orderEntity.getDelivery_method().getId());
        return dto;
    }

    public OrderEntity mapDtoToEntity(OrderDto dto, OrderEntity entity, 
    UserEntity user, ClientAddressEntity address, PaymentMethodEntity payment,
    DeliveryMethodEntity deliveryMethod){
        entity.setId(dto.getId());
        entity.setPaymentStatus(dto.getPaymentStatus());
        entity.setOrderStatus(dto.getOrderStatus());
        entity.setUser(user);
        entity.setClient_address(address);
        entity.setPayment(payment);
        entity.setDelivery_method(deliveryMethod);
        return entity;
    }
}
