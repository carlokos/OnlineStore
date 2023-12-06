package com.javaschool.OnlineStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String title;
    private double price;
    private String brand;
    private String color;
    private double weight;
    private String volume;
    private int stock;
    private Long categoryId;
}
