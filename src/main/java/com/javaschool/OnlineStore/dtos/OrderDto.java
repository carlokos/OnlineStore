package com.javaschool.OnlineStore.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String paymentStatus;
    private String orderStatus;
    private double totalPrice;
    private Date orderDate;
    private Long userId;
    private Long addressId;
    private Long paymentId;
    private Long deliveryMethodId;
}
