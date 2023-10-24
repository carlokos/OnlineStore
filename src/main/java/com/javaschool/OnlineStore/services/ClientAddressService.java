package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.dtos.ClientAddressDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.ClientAddressConverter;
import com.javaschool.OnlineStore.models.ClientAddressEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.ClientAddressRepository;
import com.javaschool.OnlineStore.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ClientAddressService {
    private final ClientAddressRepository client_AddressRepository;
    private final UserRepository userRepository;
    private final ClientAddressConverter client_AddressMapper;

    public List<ClientAddressDto> getAllAdress(){
        return client_AddressRepository.findAll().stream()
            .map(this::createAddressDto)
            .toList();
    }

    //Return all addresses of a user
    public List<ClientAddressDto> getClientAddressbyUser(Long id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return client_AddressRepository.findAllByUser(user).stream()
            .map(this::createAddressDto)
            .toList();
    }

    public ClientAddressDto getAddressById(Long id){
        ClientAddressEntity address = loadAddress(id);
        return createAddressDto(address);
    }

    public ClientAddressDto createNewAddress(ClientAddressDto dto){
        ClientAddressEntity address = mapDtoToEntity(dto, new ClientAddressEntity());
        client_AddressRepository.save(address);
        return createAddressDto(address);
    }

    public void updateAddress(Long id, ClientAddressDto dto){
        ClientAddressEntity addressEntity = loadAddress(id);
        mapDtoToEntity(dto, addressEntity);
        client_AddressRepository.save(addressEntity);
    }

    public void deleteAddress(Long id){
        client_AddressRepository.deleteById(id);
    }

    private ClientAddressDto createAddressDto(ClientAddressEntity address){
        return client_AddressMapper.createAddressDto(address);
    }

    private ClientAddressEntity loadAddress(Long id){
        return client_AddressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    private UserEntity loadUser(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ClientAddressEntity mapDtoToEntity(ClientAddressDto dto, ClientAddressEntity entity){
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
