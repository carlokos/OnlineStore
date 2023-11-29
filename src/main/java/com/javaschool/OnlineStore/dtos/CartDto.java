package com.javaschool.OnlineStore.dtos;

import lombok.Data;

@Data
public class CartDto {
    private Long id;
    private int quantity;
    private Long userId;
    private Long productId;
}
