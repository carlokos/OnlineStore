package com.javaschool.OnlineStore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javaschool.OnlineStore.dtos.ClientAddressDto;
import com.javaschool.OnlineStore.mappers.ClientAddressConverter;
import com.javaschool.OnlineStore.models.ClientAddressEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.ClientAddressRepository;
import com.javaschool.OnlineStore.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ClientAddressServiceTest {

    private final ClientAddressRepository clientAddressRepository = Mockito.mock(ClientAddressRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final ClientAddressConverter clientAddressMapper = Mockito.mock(ClientAddressConverter.class);

    private final ClientAddressService clientAddressService = new ClientAddressService(
            clientAddressRepository, userRepository, clientAddressMapper);

    @Test
    public void ClientAddressService_GetAllAddress_ReturnsAllAddresses() {
        // Arrange
        List<ClientAddressEntity> addressEntities = Collections.singletonList(new ClientAddressEntity());
        List<ClientAddressDto> expectedAddressDtos = Collections.singletonList(new ClientAddressDto());

        when(clientAddressRepository.findAll()).thenReturn(addressEntities);
        when(clientAddressMapper.createAddressDto(any())).thenReturn(new ClientAddressDto());

        // Act
        List<ClientAddressDto> actualAddressDtos = clientAddressService.getAllAdress();

        // Assert
        assertNotNull(actualAddressDtos);
        assertEquals(expectedAddressDtos.size(), actualAddressDtos.size());
    }

    @Test
    public void ClientAddressService_GetClientAddressByUser_ReturnsUserAddresses() {
        // Arrange
        Long userId = 1L;
        List<ClientAddressEntity> addressEntities = Collections.singletonList(new ClientAddressEntity());
        List<ClientAddressDto> expectedAddressDtos = Collections.singletonList(new ClientAddressDto());

        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserEntity()));
        when(clientAddressRepository.findAllByUser(any())).thenReturn(addressEntities);
        when(clientAddressMapper.createAddressDto(any())).thenReturn(new ClientAddressDto());

        // Act
        List<ClientAddressDto> actualAddressDtos = clientAddressService.getClientAddressbyUser(userId);

        // Assert
        assertNotNull(actualAddressDtos);
        assertEquals(expectedAddressDtos.size(), actualAddressDtos.size());
    }

    @Test
    public void ClientAddressService_CreateNewAddress_ReturnsCreatedAddressDto() {
        // Arrange
        ClientAddressDto addressDto = ClientAddressDto.builder()
                .userId(1L)
                .street("Sample Street")
                .city("Sample City")
                .build();

        ClientAddressEntity createdAddressEntity = ClientAddressEntity.builder()
                .id(1L)
                .user(new UserEntity())
                .street("Sample Street")
                .city("Sample City")
                .build();

        // Mock
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserEntity()));
        when(clientAddressMapper.mapDtoToEntity(any(), any(), any())).thenReturn(createdAddressEntity);
        when(clientAddressRepository.save(any())).thenReturn(createdAddressEntity);
        when(clientAddressMapper.createAddressDto(createdAddressEntity)).thenReturn(addressDto);

        // Act
        ClientAddressDto result = clientAddressService.createNewAddress(addressDto);

        // Assert
        assertNotNull(result);
        assertEquals(addressDto.getStreet(), result.getStreet());
        assertEquals(addressDto.getCity(), result.getCity());
    }

    @Test
    public void ClientAddressService_UpdateAddress_AddressIsUpdated() {
        // Arrange
        Long addressId = 1L;
        ClientAddressDto updatedAddressDto = ClientAddressDto.builder()
                .id(addressId)
                .userId(2L)
                .street("Updated Street")
                .city("Updated City")
                .build();

        ClientAddressEntity existingAddressEntity = ClientAddressEntity.builder()
                .id(addressId)
                .user(new UserEntity())
                .street("Old Street")
                .city("Old City")
                .build();

        ClientAddressEntity updateAddressEntity = existingAddressEntity;

        updateAddressEntity.setCity(updatedAddressDto.getCity());
        updateAddressEntity.setStreet(updatedAddressDto.getStreet());

        // Mock
        when(clientAddressRepository.findById(addressId)).thenReturn(java.util.Optional.of(existingAddressEntity));
        when(clientAddressMapper.mapDtoToEntity(any(), any(), any())).thenReturn(updateAddressEntity);
        when(clientAddressRepository.save(existingAddressEntity)).thenReturn(existingAddressEntity);
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new UserEntity()));

        // Act
        clientAddressService.updateAddress(addressId, updatedAddressDto);

        // Assert
        assertEquals(updatedAddressDto.getStreet(), existingAddressEntity.getStreet());
        assertEquals(updatedAddressDto.getCity(), existingAddressEntity.getCity());
        verify(clientAddressRepository, times(1)).save(existingAddressEntity);
    }

    @Test
    public void ClientAddressService_DeleteAddress_AddressIsDeleted() {
        // Arrange
        Long addressId = 1L;

        // Act
        clientAddressService.deleteAddress(addressId);

        // Assert
        verify(clientAddressRepository, times(1)).deleteById(addressId);
    }
}

