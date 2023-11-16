package com.javaschool.OnlineStore.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaschool.OnlineStore.dtos.PaymentMethodDto;
import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.mappers.PaymentMethodMapper;
import com.javaschool.OnlineStore.models.PaymentMethodEntity;
import com.javaschool.OnlineStore.repositories.PaymentMethodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    @Transactional(readOnly = true)
    public List<PaymentMethodDto> getPaymentMethods(){
        return paymentMethodRepository.findAll().stream()
            .map(this::createPaymentDto)
            .toList();
    }

    @Transactional
    public PaymentMethodDto newPaymentMethod(PaymentMethodDto dto){
        PaymentMethodEntity payment = mapDtoToEntity(dto, new PaymentMethodEntity());
        paymentMethodRepository.save(payment);
        return createPaymentDto(payment);
    }

    @Transactional
    public void updatePayment(Long id, PaymentMethodDto dto){
        PaymentMethodEntity payment = loadPayment(id);
        mapDtoToEntity(dto, payment);
        paymentMethodRepository.save(payment);
    }

    @Transactional
    public void deletePayment(Long id){
        paymentMethodRepository.deleteById(id);
    }

    private PaymentMethodEntity loadPayment(Long id){
        return paymentMethodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment method not found"));
    }

    private PaymentMethodDto createPaymentDto(PaymentMethodEntity entity){
        return paymentMethodMapper.createPaymentDto(entity);
    }

    private PaymentMethodEntity mapDtoToEntity(PaymentMethodDto dto, PaymentMethodEntity entity){
        return paymentMethodMapper.mapDtoToEntity(dto, entity);
    }
}
