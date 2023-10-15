package com.javaschool.OnlineStore.mappers;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.Client_AddressDto;
import com.javaschool.OnlineStore.models.Client_addressEntity;

@Service
public class Client_AddressMapper {
    public Client_AddressDto createAddressDto(Client_addressEntity addressEntity){
        Client_AddressDto dto = new Client_AddressDto();
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
