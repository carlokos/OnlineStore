package com.javaschool.OnlineStore.dtos;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String paymentStatus;
    private String orderStatus;
    private Long userId;
    private Long addressId;
    private Long paymentId;
    private Long deliveryMethodId;
}
