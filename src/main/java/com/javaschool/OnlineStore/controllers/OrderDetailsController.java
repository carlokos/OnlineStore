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

import com.javaschool.OnlineStore.dtos.OrderDetailDto;
import com.javaschool.OnlineStore.services.OrderDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orderDetail")
@RequiredArgsConstructor
public class OrderDetailsController {
    private final OrderDetailService orderDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDto> getOrderDetail(@PathVariable Long id){
        OrderDetailDto result = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDetailDto>> getAllOrderDetails(@PathVariable Long id){
        List<OrderDetailDto> result = orderDetailService.getAllOrderDetails(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<Long> getOrderCount(@PathVariable Long id){
        Long result = orderDetailService.getOrderDetailsCount(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/top-categories")
    public ResponseEntity<List<Object[]>> getTopSellingCategories(){
        List<Object[]> result = orderDetailService.getTopSellingCategories();
        return ResponseEntity.ok(result);
    }

    //This id represents the orderDetails id
    @GetMapping("/totalPrice/{id}")
    public ResponseEntity<Double> getTotalPrice(@PathVariable Long id){
        double result = orderDetailService.calculateTotalPrice(id);
        return ResponseEntity.ok(result);
    }

    //this id represents the Order id
    @GetMapping("/totalOrderPrice/{id}")
    public ResponseEntity<Double> getTotalOrderPrice(@PathVariable Long id){
        double result = orderDetailService.calculateOrderTotalPrice(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> createOrderDetails(@RequestBody List<OrderDetailDto> orderDetails){
        orderDetailService.createOrderDetails(orderDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order details created succesfully");        
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetailDto dto){
        orderDetailService.updateOrderDetail(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order details updated succesfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable Long id){
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Order detail deleted succesfully");
    }
}
