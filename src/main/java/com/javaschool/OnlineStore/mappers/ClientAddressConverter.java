package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Component;

import com.javaschool.OnlineStore.dtos.ClientAddressDto;
import com.javaschool.OnlineStore.models.ClientAddressEntity;

@Component
public class ClientAddressConverter {
    public ClientAddressDto createAddressDto(ClientAddressEntity addressEntity){
        ClientAddressDto dto = new ClientAddressDto();
        dto.setCountry(addressEntity.getCountry());
        dto.setApartament(addressEntity.getApartament());
        dto.setCity(addressEntity.getCity());
        dto.setHome(addressEntity.getHome());
        dto.setPostal_code(addressEntity.getPostal_code());
        dto.setStreet(addressEntity.getStreet());
        dto.setUser_email(addressEntity.getUser().getEmail());
        return dto;
    }
    
}
