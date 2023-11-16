package com.javaschool.OnlineStore.dtos;

import lombok.Data;

@Data
public class OrderDetailDto {
    private Long id;
    private int quantity;
    private Long orderId;
    private Long productId;
}
