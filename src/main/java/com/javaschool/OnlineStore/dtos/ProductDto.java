package com.javaschool.OnlineStore.dtos;

import lombok.Data;

@Data
public class ProductDto {
    private String title;
    private double price;
    private String brand;
    private String color;
    private double weight;
    private String volume;
    private int stock;
    private String category;
}
