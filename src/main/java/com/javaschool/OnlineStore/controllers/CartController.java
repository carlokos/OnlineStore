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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaschool.OnlineStore.dtos.CartDto;
import com.javaschool.OnlineStore.services.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<List<CartDto>> getUserCart(@PathVariable Long id) {
        List<CartDto> result = cartService.getUserCart(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> createNewCart(@RequestBody CartDto dto) {
        cartService.newCart(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cart created succesfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCart(@PathVariable Long id, @RequestBody CartDto dto) {
        cartService.updateCart(id, dto);
        return ResponseEntity.ok("Cart updated succesfully");
    }

    @PutMapping("/{userId}/{productId}")
    public ResponseEntity<String> addQuantityToProduct(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam(required = false, defaultValue = "1") int quantity) {
        cartService.modifyProductCart(userId, productId, quantity);
        return ResponseEntity.ok("Cart updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cart deleted succesfully");
    }

    // this id represents the User id not the cart id
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> clearUserCart(@PathVariable Long id) {
        cartService.clearUserCart(id);
        return ResponseEntity.status((HttpStatus.NO_CONTENT)).body("User Cart cleared succesfully");
    }
}
