package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaschool.OnlineStore.dtos.ClientAddressDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.ClientAddressConverter;
import com.javaschool.OnlineStore.models.ClientAddressEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.ClientAddressRepository;
import com.javaschool.OnlineStore.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientAddressService {
    private final ClientAddressRepository clientAddressRepository;
    private final UserRepository userRepository;
    private final ClientAddressConverter clientAddressMapper;

    @Transactional(readOnly = true)
    public List<ClientAddressDto> getAllAdress(){
        return clientAddressRepository.findAll().stream()
            .map(this::createAddressDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ClientAddressDto> getClientAddressbyUser(Long id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return clientAddressRepository.findAllByUser(user).stream()
            .map(this::createAddressDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public ClientAddressDto getAddressById(Long id){
        ClientAddressEntity address = loadAddress(id);
        return createAddressDto(address);
    }

    @Transactional
    public ClientAddressDto createNewAddress(ClientAddressDto dto){
        ClientAddressEntity address = mapDtoToEntity(dto, new ClientAddressEntity());
        clientAddressRepository.save(address);
        return createAddressDto(address);
    }

    @Transactional
    public void updateAddress(Long id, ClientAddressDto dto){
        ClientAddressEntity addressEntity = loadAddress(id);
        mapDtoToEntity(dto, addressEntity);
        clientAddressRepository.save(addressEntity);
    }

    @Transactional
    public void deleteAddress(Long id){
        clientAddressRepository.deleteById(id);
    }

    private ClientAddressDto createAddressDto(ClientAddressEntity address){
        return clientAddressMapper.createAddressDto(address);
    }

    private ClientAddressEntity loadAddress(Long id){
        return clientAddressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    private UserEntity loadUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ClientAddressEntity mapDtoToEntity(ClientAddressDto dto, ClientAddressEntity entity){
       return clientAddressMapper.mapDtoToEntity(dto, entity, loadUser(dto.getUserId()));
    }
}
