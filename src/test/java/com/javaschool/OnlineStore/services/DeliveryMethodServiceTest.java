package com.javaschool.OnlineStore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javaschool.OnlineStore.dtos.DeliveryMethodDto;
import com.javaschool.OnlineStore.mappers.DeliveryMethodMapper;
import com.javaschool.OnlineStore.models.DeliveryMethodEntity;
import com.javaschool.OnlineStore.repositories.DeliveryMethodRepository;

@ExtendWith(MockitoExtension.class)
public class DeliveryMethodServiceTest {

    private final DeliveryMethodRepository deliveryMethodRepository = Mockito.mock(DeliveryMethodRepository.class);
    private final DeliveryMethodMapper deliveryMethodMapper = Mockito.mock(DeliveryMethodMapper.class);

    private final DeliveryMethodService deliveryMethodService = new DeliveryMethodService(
            deliveryMethodRepository, deliveryMethodMapper);

    @Test
    public void DeliveryMethodService_GetDeliveryMethods_ReturnsAllDeliveryMethods() {
        // Arrange
        List<DeliveryMethodEntity> deliveryMethodEntities = Collections.singletonList(new DeliveryMethodEntity());
        List<DeliveryMethodDto> expectedDeliveryMethodDtos = Collections.singletonList(new DeliveryMethodDto());

        when(deliveryMethodRepository.findAll()).thenReturn(deliveryMethodEntities);
        when(deliveryMethodMapper.createDeliveryDto(any())).thenReturn(new DeliveryMethodDto());

        // Act
        List<DeliveryMethodDto> actualDeliveryMethodDtos = deliveryMethodService.getDeliveryMethods();

        // Assert
        assertNotNull(actualDeliveryMethodDtos);
        assertEquals(expectedDeliveryMethodDtos.size(), actualDeliveryMethodDtos.size());
    }

    @Test
    public void DeliveryMethodService_NewDeliveryMethod_ReturnsCreatedDeliveryMethodDto() {
        // Arrange
        DeliveryMethodDto deliveryMethodDto = DeliveryMethodDto.builder()
                .DeliveryMethod(("Express"))
                .build();

        DeliveryMethodEntity createdDeliveryMethodEntity = DeliveryMethodEntity.builder()
                .id(1L)
                .d_method("Express")
                .build();

        // Mock
        when(deliveryMethodMapper.mapDtoToEntity(any(), any())).thenReturn(createdDeliveryMethodEntity);
        when(deliveryMethodRepository.save(any())).thenReturn(createdDeliveryMethodEntity);
        when(deliveryMethodMapper.createDeliveryDto(createdDeliveryMethodEntity)).thenReturn(deliveryMethodDto);

        // Act
        DeliveryMethodDto result = deliveryMethodService.newDeliveryMethod(deliveryMethodDto);

        // Assert
        assertNotNull(result);
        assertEquals(deliveryMethodDto.getDeliveryMethod(), result.getDeliveryMethod());
    }

    @Test
    public void DeliveryMethodService_UpdateDeliveryMethod_DeliveryMethodIsUpdated() {
        // Arrange
        Long deliveryMethodId = 1L;
        DeliveryMethodDto updatedDeliveryMethodDto = DeliveryMethodDto.builder()
                .id(deliveryMethodId)
                .DeliveryMethod("Updated Express")
                .build();

        DeliveryMethodEntity existingDeliveryMethodEntity = DeliveryMethodEntity.builder()
                .id(deliveryMethodId)
                .d_method("Express")
                .build();

        DeliveryMethodEntity updatedDeliveryMethodEntity = existingDeliveryMethodEntity;

        updatedDeliveryMethodEntity.setD_method(updatedDeliveryMethodDto.getDeliveryMethod());

        // Mock
        when(deliveryMethodRepository.findById(deliveryMethodId)).thenReturn(Optional.of(existingDeliveryMethodEntity));
        when(deliveryMethodMapper.mapDtoToEntity(any(), any())).thenReturn(updatedDeliveryMethodEntity);
        when(deliveryMethodRepository.save(existingDeliveryMethodEntity)).thenReturn(existingDeliveryMethodEntity);

        // Act
        deliveryMethodService.updateDelivery(deliveryMethodId, updatedDeliveryMethodDto);

        // Assert
        assertEquals(updatedDeliveryMethodDto.getDeliveryMethod(), existingDeliveryMethodEntity.getD_method());
        verify(deliveryMethodRepository, times(1)).save(existingDeliveryMethodEntity);
    }

    @Test
    public void DeliveryMethodService_DeleteDeliveryMethod_DeliveryMethodIsDeleted() {
        // Arrange
        Long deliveryMethodId = 1L;

        // Act
        deliveryMethodService.deleteDeliveryMethod(deliveryMethodId);

        // Assert
        verify(deliveryMethodRepository, times(1)).deleteById(deliveryMethodId);
    }
}

