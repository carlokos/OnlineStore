package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.PaymentMethodDto;
import com.javaschool.OnlineStore.models.PaymentMethodEntity;

@Service
public class PaymentMethodMapper {
    public PaymentMethodDto createPaymentDto(PaymentMethodEntity entity){
        PaymentMethodDto dto = new PaymentMethodDto();
        dto.setId(entity.getId());
        dto.setPaymentMethod(entity.getP_method());
        return dto;
    }

    public PaymentMethodEntity mapDtoToEntity(PaymentMethodDto dto, PaymentMethodEntity entity){
        entity.setId(dto.getId());
        entity.setP_method(dto.getPaymentMethod());
        return entity;
    }
}
