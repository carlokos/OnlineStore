package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaschool.OnlineStore.dtos.DeliveryMethodDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.DeliveryMethodMapper;
import com.javaschool.OnlineStore.models.DeliveryMethodEntity;
import com.javaschool.OnlineStore.repositories.DeliveryMethodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryMethodService {
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final DeliveryMethodMapper deliveryMethodMapper;

    @Transactional(readOnly = true)
    public List<DeliveryMethodDto> getDeliveryMethods(){
        return deliveryMethodRepository.findAll().stream()
            .map(this::createDeliveryDto)
            .toList();
    }

    @Transactional
    public DeliveryMethodDto newDeliveryMethod(DeliveryMethodDto dto){
        DeliveryMethodEntity delivery = mapDtoToEntity(dto, new DeliveryMethodEntity());
        deliveryMethodRepository.save(delivery);
        return createDeliveryDto(delivery);
    }

    @Transactional
    public void updateDelivery(Long id, DeliveryMethodDto dto){
        DeliveryMethodEntity delivery = loadDelivery(id);
        mapDtoToEntity(dto, delivery);
        deliveryMethodRepository.save(delivery);
    }

    @Transactional
    public void deleteDeliveryMethod(Long id){
        deliveryMethodRepository.deleteById(id);
    }

    private DeliveryMethodEntity loadDelivery(Long id){
        return deliveryMethodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delivery method not found"));
    }

    private DeliveryMethodDto createDeliveryDto(DeliveryMethodEntity entity){
        return deliveryMethodMapper.createDeliveryDto(entity);
    }

    private DeliveryMethodEntity mapDtoToEntity(DeliveryMethodDto dto, DeliveryMethodEntity entity){
        return deliveryMethodMapper.mapDtoToEntity(dto, entity);
    }
}
