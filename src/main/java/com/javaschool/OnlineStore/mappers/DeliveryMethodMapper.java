package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.DeliveryMethodDto;
import com.javaschool.OnlineStore.models.DeliveryMethodEntity;

@Service
public class DeliveryMethodMapper {
    public DeliveryMethodDto createDeliveryDto(DeliveryMethodEntity entity){
        DeliveryMethodDto dto = new DeliveryMethodDto();
        dto.setId(entity.getId());
        dto.setDeliveryMethod(entity.getD_method());
        return dto;
    }

    public DeliveryMethodEntity mapDtoToEntity(DeliveryMethodDto dto, DeliveryMethodEntity entity){
        entity.setId(dto.getId());
        entity.setD_method(dto.getDeliveryMethod());
        return entity;
    }
}
