package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Component;

import com.javaschool.OnlineStore.dtos.ClientAddressDto;
import com.javaschool.OnlineStore.models.ClientAddressEntity;
import com.javaschool.OnlineStore.models.UserEntity;

@Component
public class ClientAddressConverter {
    public ClientAddressDto createAddressDto(ClientAddressEntity addressEntity){
        ClientAddressDto dto = new ClientAddressDto();
        dto.setId(addressEntity.getId());
        dto.setCountry(addressEntity.getCountry());
        dto.setApartament(addressEntity.getApartament());
        dto.setCity(addressEntity.getCity());
        dto.setHome(addressEntity.getHome());
        dto.setPostalCode(addressEntity.getPostal_code());
        dto.setStreet(addressEntity.getStreet());
        dto.setUserId(addressEntity.getUser().getId());
        return dto;
    }
    
    public ClientAddressEntity mapDtoToEntity(ClientAddressDto dto, ClientAddressEntity entity,
    UserEntity user){
        entity.setId(dto.getId());
        entity.setCountry(dto.getCountry());
        entity.setApartament(dto.getApartament());
        entity.setCity(dto.getCity());
        entity.setHome(dto.getHome());
        entity.setPostal_code(dto.getPostalCode());
        entity.setStreet(dto.getStreet());
        entity.setUser(user);
        return entity;
    }
}
