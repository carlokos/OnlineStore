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

import com.javaschool.OnlineStore.dtos.OrderDto;
import com.javaschool.OnlineStore.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderDto>> getUserOrder(@PathVariable Long id){
       List<OrderDto> result = orderService.getAllUserOrders(id);
       return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDto dto){
        orderService.newOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order created succesfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody OrderDto dto){
        orderService.updateOrder(id, dto);
        return ResponseEntity.ok("Order updated succesfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
        orderService.deleteCart(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Order deleted succesfully");
    }
}
