package com.javaschool.OnlineStore.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaschool.OnlineStore.dtos.PaymentMethodDto;
import com.javaschool.OnlineStore.services.PaymentMethodService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public ResponseEntity<List<PaymentMethodDto>> getAllPaymentMethods(){
        List<PaymentMethodDto> result = paymentMethodService.getPaymentMethods();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> createNewPaymentMethod(@RequestBody PaymentMethodDto dto){
        paymentMethodService.newPaymentMethod(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Payment method created succesfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePaymentMethod(@PathVariable Long id, @RequestBody PaymentMethodDto dto){
        paymentMethodService.updatePayment(id, dto);
        return ResponseEntity.ok("Payment Method updated succesfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable Long id){
        paymentMethodService.deletePayment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Payment Method deleted succesfully");
    }
}
