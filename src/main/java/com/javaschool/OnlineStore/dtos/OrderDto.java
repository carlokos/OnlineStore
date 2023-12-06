package com.javaschool.OnlineStore.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
