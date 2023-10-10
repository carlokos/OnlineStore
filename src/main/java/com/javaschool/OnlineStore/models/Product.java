package com.javaschool.OnlineStore.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
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
	
	@ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Order_detail> Order_detail;
	
}
