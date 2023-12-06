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

import com.javaschool.OnlineStore.dtos.PaymentMethodDto;
import com.javaschool.OnlineStore.mappers.PaymentMethodMapper;
import com.javaschool.OnlineStore.models.PaymentMethodEntity;
import com.javaschool.OnlineStore.repositories.PaymentMethodRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentMethodServiceTest {

    private final PaymentMethodRepository paymentMethodRepository = Mockito.mock(PaymentMethodRepository.class);

    private final PaymentMethodMapper paymentMethodMapper = Mockito.mock(PaymentMethodMapper.class);

    private final PaymentMethodService paymentMethodService = new PaymentMethodService(
            paymentMethodRepository, paymentMethodMapper);

    @Test
    public void PaymentMethodService_GetPaymentMethods_ReturnsPaymentMethods() {
        // Arrange
        List<PaymentMethodEntity> paymentMethods = Collections.singletonList(PaymentMethodEntity.builder().build());
        List<PaymentMethodDto> expectedPaymentMethodDtos = Collections.singletonList(PaymentMethodDto.builder().build());

        when(paymentMethodRepository.findAll()).thenReturn(paymentMethods);
        when(paymentMethodMapper.createPaymentDto(any())).thenReturn(expectedPaymentMethodDtos.get(0));

        // Act
        List<PaymentMethodDto> actualPaymentMethodDtos = paymentMethodService.getPaymentMethods();

        // Assert
        assertNotNull(actualPaymentMethodDtos);
        assertEquals(expectedPaymentMethodDtos.size(), actualPaymentMethodDtos.size());
    }

    @Test
    public void PaymentMethodService_NewPaymentMethod_CreatesAndReturnsPaymentMethodDto() {
        // Arrange
        PaymentMethodDto dto = PaymentMethodDto.builder().build();
        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.builder().build();

        // Mocks
        when(paymentMethodMapper.mapDtoToEntity(any(), any())).thenReturn(paymentMethodEntity);
        when(paymentMethodRepository.save(paymentMethodEntity)).thenReturn(paymentMethodEntity);
        when(paymentMethodMapper.createPaymentDto(paymentMethodEntity)).thenReturn(dto);

        // Act
        PaymentMethodDto result = paymentMethodService.newPaymentMethod(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    public void PaymentMethodService_UpdatePayment_UpdatesPayment() {
        // Arrange
        Long paymentId = 1L;
        PaymentMethodDto dto = PaymentMethodDto.builder().build();
        PaymentMethodEntity paymentMethodEntity = PaymentMethodEntity.builder().build();

        // Mocks
        when(paymentMethodRepository.findById(paymentId)).thenReturn(Optional.of(paymentMethodEntity));
        when(paymentMethodMapper.mapDtoToEntity(any(), any())).thenReturn(paymentMethodEntity);
        when(paymentMethodRepository.save(paymentMethodEntity)).thenReturn(paymentMethodEntity);

        // Act
        paymentMethodService.updatePayment(paymentId, dto);

        // Assert
        verify(paymentMethodRepository, times(1)).save(paymentMethodEntity);
    }

    @Test
    public void PaymentMethodService_DeletePayment_DeletesPayment() {
        // Arrange
        Long paymentId = 1L;

        // Act
        paymentMethodService.deletePayment(paymentId);

        // Assert
        verify(paymentMethodRepository, times(1)).deleteById(paymentId);
    }
}
