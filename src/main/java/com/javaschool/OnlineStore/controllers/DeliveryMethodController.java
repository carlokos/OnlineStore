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

import com.javaschool.OnlineStore.dtos.DeliveryMethodDto;
import com.javaschool.OnlineStore.services.DeliveryMethodService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryMethodController {
    private final DeliveryMethodService deliveryMethodService;

    @GetMapping
    public ResponseEntity<List<DeliveryMethodDto>> getAllDeliveryMethods(){
        List<DeliveryMethodDto> result = deliveryMethodService.getDeliveryMethods();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> createNewDeliveryMethod(@RequestBody DeliveryMethodDto dto){
        deliveryMethodService.newDeliveryMethod(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Delivery method created succesfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDeliveryMethod(@PathVariable Long id, @RequestBody DeliveryMethodDto dto){
        deliveryMethodService.updateDelivery(id, dto);
        return ResponseEntity.ok("Delivery Method updated succesfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeliveryMethod(@PathVariable Long id){
        deliveryMethodService.deleteDeliveryMethod(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Delivery Method deleted succesfully");
    }
}
