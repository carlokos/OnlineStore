package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.Client_AddressDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.Client_AddressMapper;
import com.javaschool.OnlineStore.models.Client_addressEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.Client_AddressRepository;
import com.javaschool.OnlineStore.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class Client_AddressService {
    private final Client_AddressRepository client_AddressRepository;
    private final UserRepository userRepository;
    private final Client_AddressMapper client_AddressMapper;

    public List<Client_AddressDto> getAllAdress(){
        return client_AddressRepository.findAll().stream()
            .map(this::createAddressDto)
            .toList();
    }

    //Return all addresses of a user
    public List<Client_AddressDto> getClientAddressbyUser(Long id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return client_AddressRepository.findAllByUser(user).stream()
            .map(this::createAddressDto)
            .toList();
    }

    public Client_AddressDto getAddressById(Long id){
        Client_addressEntity address = loadAddress(id);
        return createAddressDto(address);
    }

    public Client_AddressDto createNewAddress(Client_AddressDto dto){
        Client_addressEntity address = mapDtoToEntity(dto, new Client_addressEntity());
        client_AddressRepository.save(address);
        return createAddressDto(address);
    }

    public void updateAddress(Long id, Client_AddressDto dto){
        Client_addressEntity addressEntity = loadAddress(id);
        mapDtoToEntity(dto, addressEntity);
        client_AddressRepository.save(addressEntity);
    }

    public void deleteAddress(Long id){
        client_AddressRepository.deleteById(id);
    }

    private Client_AddressDto createAddressDto(Client_addressEntity address){
        return client_AddressMapper.createAddressDto(address);
    }

    private Client_addressEntity loadAddress(Long id){
        return client_AddressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    private UserEntity loadUser(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Client_addressEntity mapDtoToEntity(Client_AddressDto dto, Client_addressEntity entity){
        entity.setApartament(dto.getApartament());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setHome(dto.getHome());
        entity.setPostal_code(dto.getPostal_code());
        entity.setStreet(dto.getStreet());
        entity.setUser(loadUser(dto.getUser_email()));
        return entity;
    }
}
