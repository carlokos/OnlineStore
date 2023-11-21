package com.javaschool.OnlineStore.controllers;

import java.util.List;
import java.util.Map;

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

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        List<OrderDto> result = orderService.getAllOrders();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/montly-revenue/{month}/{year}")
    public ResponseEntity<Double> getMonthly(@PathVariable int month, @PathVariable int year){
        double result = orderService.getMonthlyRevenue(month, year);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/weekly-revenue/{month}/{year}")
    public ResponseEntity<List<Map<String, Object>>> getWeeklyRevenueByMonth(@PathVariable int month, @PathVariable int year){
        List<Map<String, Object>> result = orderService.getWeeklyRevenue(month, year);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto dto){
        OrderDto result = orderService.newOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
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
