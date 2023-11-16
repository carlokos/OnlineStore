package com.javaschool.OnlineStore.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(length=50, nullable = false, unique=true)
    private String title;
	
    @Column(nullable = false, unique=false)
    private double price;
	
    @Column(length=50, nullable = false, unique=false)
    private String brand;
	
    @Column(length=50, nullable = false, unique=false)
    private String color;
	
    @Column(nullable = false, unique=false)
    private double weight;

    @Column(length=50, nullable = false, unique=false)
    private String volume;
	
    @Column(nullable = false, unique=false)
    private int stock;
	
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
	
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetailEntity> Order_detail;
	
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartEntity> cart;
}
